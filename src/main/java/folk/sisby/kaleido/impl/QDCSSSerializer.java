/*
 * Copyright 2022, 2023 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package folk.sisby.kaleido.impl;

import folk.sisby.kaleido.lib.qdcss.QDCSS;
import org.quiltmc.config.api.Config;
import org.quiltmc.config.api.Constraint;
import org.quiltmc.config.api.Serializer;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.exceptions.ConfigParseException;
import org.quiltmc.config.api.values.CompoundConfigValue;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.api.values.ValueTreeNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class QDCSSSerializer implements Serializer {
    @Override
    public String getFileExtension() {
        return "css";
    }

    @Override
    public void serialize(Config config, OutputStream to) throws IOException {
        for (ValueTreeNode node : config.nodes()) {
            List<String> comments = new ArrayList<>();

            if (node.hasMetadata(Comment.TYPE)) {
                for (String string : node.metadata(Comment.TYPE)) {
                    comments.add(string);
                }
            }

            if (node instanceof TrackedValue<?> trackedValue) {
                Object defaultValue = trackedValue.getDefaultValue();

                if (defaultValue.getClass().isEnum()) {
                    StringBuilder options = new StringBuilder("options: ");
                    Object[] enumConstants = defaultValue.getClass().getEnumConstants();

                    for (int i = 0, enumConstantsLength = enumConstants.length; i < enumConstantsLength; i++) {
                        Object o = enumConstants[i];

                        options.append(o);

                        if (i < enumConstantsLength - 1) {
                            options.append(", ");
                        }
                    }

                    comments.add(options.toString());
                }

                for (Constraint<?> constraint : trackedValue.constraints()) {
                    comments.add(constraint.getRepresentation());
                }

                if (!(defaultValue instanceof CompoundConfigValue<?>)) {
                    comments.add("default: " + defaultValue);
                }

                for (String comment : comments) {
                    // Todo write css comments
                }

                String key = trackedValue.key().toString();
                Object value = trackedValue.getRealValue();
                // Todo write the actual css entry idk
            } else if (node instanceof ValueTreeNode.Section section) {
                // Todo write sections??? ughhhh
            }
        }
    }

    @Override
    public void deserialize(Config config, InputStream from) throws IOException {
        QDCSS read = QDCSS.load(config.id(), from);

        for (TrackedValue<?> trackedValue : config.values()) {
            if (read.containsKey(trackedValue.key().toString())) {
                ((TrackedValue) trackedValue).setValue(coerceQDCSS(trackedValue.key().toString(), read, trackedValue.getDefaultValue()), false);
            }
        }
    }

    private static Object coerceQDCSS(String key, QDCSS read, Object to) {
        if (to instanceof Integer) {
            return read.getInt(key).get();
        } else if (to instanceof Long) {
            return read.getInt(key).get();
        } else if (to instanceof Float) {
            return read.getDouble(key).get();
        } else if (to instanceof Double) {
            return read.getDouble(key).get();
        } else if (to instanceof String) {
            return read.get(key).get();
        } else if (to instanceof Boolean) {
            return read.getBoolean(key).get();
        } else if (to.getClass().isEnum()) {
            for (Object o : to.getClass().getEnumConstants()) {
                if (((Enum<?>) o).name().equalsIgnoreCase(read.get(key).get())) {
                    return o;
                }
            }
            throw new ConfigParseException("Unexpected value '" + read + "' for enum class '" + to.getClass() + "'");
        } else {
            throw new ConfigParseException("Unexpected value type: " + to.getClass());
        }
        // Todo object, map, list handling (how??)
    }
}