package ru.hoty.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import ru.hoty.collection.CollectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.hoty.parser.DataParser;

/**
 * Class for saving org.hoty.collection to file.
 */
public class Save {

    static final Logger logger = LogManager.getLogger();

    /**
     * Saves org.hoty.collection to file.
     * 
     * @return true on success and false if not.
     */
    public boolean execute() {
        File fFile = new File(CollectionManager.getFile());
        if(!fFile.exists() || !fFile.canWrite()) {
            logger.error("Can't save collection. File doesn't exist or opened with Only-Read flag.");
            return false;
        }
        try (OutputStream oStream = new FileOutputStream(CollectionManager.getFile())) {
            OutputStreamWriter oWriter = new OutputStreamWriter(oStream);
            String[] data = DataParser.csvToString();
            for (String out : data) {
                oWriter.write(out);
            }
            oWriter.close();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        logger.info("Collection has been successfully saved.");
        return true;
    }
}
