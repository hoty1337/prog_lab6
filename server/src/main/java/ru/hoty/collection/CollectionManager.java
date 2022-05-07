    package ru.hoty.collection;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileReader;
    import java.io.IOException;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Collection;
    import java.util.LinkedHashMap;
    import java.util.Map;
    import java.util.Set;
    import java.util.Map.Entry;

import ru.hoty.parser.DataParser;
    import ru.hoty.utils.TextFormatter;

    public class CollectionManager {
        private static Map<Long, Ticket> ticket;
        private static final String[] nameValues = {"id","name","coordinates","price","comment","type","venue"};
        private static String initTime;
        private static String filePath;
        private static BufferedReader file;

        public static boolean setFilePath(String env) {
            filePath = "input.csv";//System.getenv(env);
            File fFile;
            ticket = new LinkedHashMap<>();
            initTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"));
            try {
                fFile = new File(filePath);
                file = new BufferedReader(new FileReader(fFile));
            } catch (NullPointerException e) {
                System.out.println(TextFormatter.toRed("Путь к файлу не указан! Укажите значение переменной окружения FILE_PATH.\nВыход из программы."));
                System.exit(0);
                return false;
            } catch(FileNotFoundException e) {
                System.out.println(TextFormatter.toRed("Файл (" + filePath + ") не найден или не имеет доступа к чтению!\nВыход из программы."));
                System.exit(0);
                return false;
            }
            if(!fFile.canWrite()) {
                System.out.println(TextFormatter.toYellow("Файл (" + filePath + ") открыт в режиме чтения!"));
            }
            return true;
        }

        public static boolean loadCollection() {
            try {
                String str;
                while((str = file.readLine()) != null) {
                    String[] data = str.split(",");
                    Ticket tempTicket = DataParser.stringToCsv(data);
                    if(checkIsCorrect(tempTicket)) {
                        ticket.put(tempTicket.getId(), tempTicket);
                    } else {
                        System.out.println(TextFormatter.toRed("Элемент с ID " + tempTicket.getId() + 
                                " сформирован неправильно и не был добавлен!"));
                    }
                }
            } catch (IOException e) {
                System.out.println(TextFormatter.toRed("При чтении файла что-то пошло не так... Попробуйте ещё раз."));
                return false;
            }
            return true;
        }

        public static boolean checkIsCorrect(Ticket ticket) {
            if(ticket.getName() == null) {
                return false;
            } else if(ticket.getCoordinates() == null || ticket.getCoordinates().getX() > 846 ||
                    ticket.getCoordinates().getY() > 672) {
                return false;
            } else if(ticket.getPrice() == null || ticket.getPrice() <= 0) {
                return false;
            }
            return ticket.getVenue() != null && ticket.getVenue().getName() != null &&
                    ticket.getVenue().getCapacity() > 0;
        }

        public static String[] getNameValues() {
            return nameValues;
        }

        public static String getInfo() {
            return TextFormatter.toGreen("----------------------------INFO----------------------------\n")
                +  "Тип коллекции:\t\t" + TextFormatter.toPurple("LinkedHashMap\n") 
                +  "Тип элементов:\t\t" + TextFormatter.toPurple("Ticket\n")
                +  "Количество элементов:\t\t" + TextFormatter.toPurple(ticket.size() + "\n")
                +  "Приоритет:\t\t" + TextFormatter.toPurple("Price\n")
                +  "Дата инициализации:\t\t" + TextFormatter.toPurple(initTime + "\n")
                +  TextFormatter.toGreen("----------------------------INFO----------------------------\n");
        }

        public static boolean removeKey(Long key) {
            return ticket.remove(key) != null;
        }

        public static boolean removeKey(Set<Long> keys) {
            for (Long uId : keys) {
                if(ticket.remove(uId) == null) {
                    return false;
                }
            }
            return true;
        }

        public static void clear() {
            ticket.clear();
        }

        public static boolean exist(Long uId) {
            return ticket.get(uId) != null;
        }

        public static void put(Long uId, Ticket tTicket) {
            ticket.put(uId, tTicket);
        }

        public static void replace(Long uId, Ticket tTicket) {
            ticket.replace(uId, tTicket);
        }

        public static String getFile() {
            return filePath;
        }

        public static int getSize() {
            return ticket.size();
        }

        public static Collection<Ticket> values() {
            return ticket.values();
        }

        public static Ticket get(Long uId) {
            return ticket.get(uId);
        }

        public static Set<Entry<Long, Ticket>> entrySet() {
            return ticket.entrySet();
        }

        public static Set<Long> keySet() {
            return ticket.keySet();
        }
    }
