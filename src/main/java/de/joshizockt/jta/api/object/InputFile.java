package de.joshizockt.jta.api.object;

import java.io.File;

public interface InputFile {

    static InputFile of(File file) {
        return of(file.getName(), file);
    }

    static InputFile of(String name, File file) {
        return new InputFile() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public File file() {
                return file;
            }
        };
    }

    String name();
    File file();

}
