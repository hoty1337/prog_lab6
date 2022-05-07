package ru.hoty.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.hoty.parser.DataParser;

/**
 * Class for saving org.hoty.collection to file.
 */
public class Save implements CommandInterface {

    static final Logger logger = LogManager.getLogger();

    /**
     * Class constructor
     */
    public Save() {
        Help.addCmd("save", " - saves org.hoty.collection to file.");
    }

    /**
     * Saves org.hoty.collection to file.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        File fFile = new File(CollectionManager.getFile());
        if(!fFile.exists() || !fFile.canWrite()) {
            logger.error("Can't save org.hoty.collection. File doesn't exist or opened with Only-Read flag.");
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
