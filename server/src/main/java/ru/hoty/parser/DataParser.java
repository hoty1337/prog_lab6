package ru.hoty.parser;

import java.time.LocalDateTime;
import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Coordinates;
import ru.hoty.collection.Ticket;
import ru.hoty.collection.TicketType;
import ru.hoty.collection.Venue;
import ru.hoty.utils.TextFormatter;

/**
 * Class for converting data from CSV to array 
 * of strings and array of strings to CSV
 */
public class DataParser {
    
    /**
     * Converts array of strings to class Ticket
     * 
     * @param data      - Array of data from strings
     * @return          - Converted data to class Ticket
     */
    public static Ticket stringToCsv(String[] data) {
        Ticket tempTicket = new Ticket();
        Long uId = null;
        for(int i = 0; i < CollectionManager.getNameValues().length; i++) {
            try {
                switch(CollectionManager.getNameValues()[i]) {
                    case "id": {
                        if(data[i] == null) break;
                        uId = Long.parseLong(data[i]);
                        if(CollectionManager.exist(uId)) {
                            throw new Exception();
                        }
                        tempTicket.setId(uId);
                        break;
                    }
                    case "name": {
                        if(data[i] == null) break;
                        tempTicket.setName(data[i]);
                        break;
                    } 
                    case "coordinates": {
                        String[] coords = data[i].split(";");
                        tempTicket.setCoordinates(new Coordinates(Integer.parseInt(coords[0]), 
                                                                Float.parseFloat(coords[1])));
                        break;
                    } 
                    case "price": {
                        tempTicket.setPrice(Float.parseFloat(data[i]));
                        break;
                    }
                    case "comment": {
                        tempTicket.setComment(data[i]);
                        break;
                    }
                    case "type": {
                        tempTicket.setType(TicketType.valueOf(data[i].trim()));
                        break;
                    }
                    case "venue": {
                        if(data[i] == null) break;
                        String[] venue = data[i].split(";");
                        tempTicket.setVenue(new Venue(Long.parseLong(uId.toString()), venue[0], Integer.parseInt(venue[1])));
                        break;
                    }
                }
            } catch(Exception e) {
                System.out.println(TextFormatter.toRed("Одно из значений элемента указано неверно, пропускаем элемент..."));
                break;
            }
        }
        tempTicket.setId(uId);
        tempTicket.setCreationDate(LocalDateTime.now());
        return tempTicket;
    }
/**
     * Converts Ticket fields to array of strings
     * 
     * @param cManager  - Class to work with org.hoty.collection
     * @return          - Converted Ticket data to strings
     */
    public static String[] csvToString() {
        String[] outputData = new String[CollectionManager.getSize()];
        int i = 0;
        for (Ticket t : CollectionManager.values()) {
            outputData[i++] = t.getId() + "," + t.getName() + "," + t.getCoordinates().getX() + ";" + t.getCoordinates().getY() + "," +
            t.getPrice() + "," + t.getComment() + "," + t.getType() + "," + t.getVenue().getName() + ";" + t.getVenue().getCapacity() + "\n";
        }

        return outputData;
    }
}
