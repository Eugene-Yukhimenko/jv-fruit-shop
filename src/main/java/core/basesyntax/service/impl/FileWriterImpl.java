package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriterImpl implements FileWriter {

    @Override
    public void write(String data, String path) throws IOException {
        Files.write(Paths.get(path), data.getBytes(StandardCharsets.UTF_8));
    }
}
