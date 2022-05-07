package ru.hoty.utils;

import java.time.LocalDateTime;

import ru.hoty.collection.Coordinates;
import ru.hoty.collection.Ticket;
import ru.hoty.collection.TicketType;
import ru.hoty.collection.Venue;

public class CreateObject {
    private static String[] nameValues = {"id","name","coordinates","price","comment","type","venue"};


    public static Ticket invoke(SocketWorker sWorker, Ticket ticket, String type) {
        String el;
        Long uId;
        if(type.equals("insert")) {
            System.out.println("Введите уникальный ID:");
        } else {
            System.out.println("Введите ID, который хотите обновить:");
        }
        while (true) {
            el = ConsoleWorker.readLine();
            if(el == null) {
                continue;
            }
            try {
                uId = Long.parseLong(el);
            } catch (Exception e) {
                System.out.println(TextFormatter.toRed("ID должен быть числом!"));
                continue;
            }
            if(uId <= 0) {
                System.out.println(TextFormatter.toRed("ID должен быть больше нуля!"));
                continue;
            }
            sWorker.write(new Command("get", uId));
            Answer ans = sWorker.read();
            if(!type.equals("insert") && ans.getAnswer().equals("")) {
                System.out.println(TextFormatter.toRed("Элемента с таким ID не существует!"));
                continue;
            } else if(type.equals("insert") && !ans.getAnswer().equals("")) {
                System.out.println(TextFormatter.toRed("Элемент с таким ID уже существует!"));
                continue;
            }
            ticket.setId(uId);
            break;
        }
        for(int i = 0; i < nameValues.length; i++) {
            if(nameValues[i].equals("name")) {
                System.out.println("Введите name:");
                el = ConsoleWorker.readLine();
                if(el == null) {
                    return null;
                }
                ticket.setName(el);
            } else if(nameValues[i].equals("coordinates")) {
                System.out.println("Введите coordinates в формате X;Y (X <= 846, Y <= 672)");
                el = ConsoleWorker.readLine();
                if(el == null) {
                    return null;
                }
                if(el.indexOf(";") == el.lastIndexOf(";")) {
                    String[] coords = el.split(";");
                    try {
                        Integer x = Integer.parseInt(coords[0]); 
                        Float y = Float.parseFloat(coords[1]);
                        if(x > 846 || y > 672) {
                            System.out.println(TextFormatter.toRed("Вы превысили максимальное значение координаты!"));
                            i--;
                            continue;
                        }
                        ticket.setCoordinates(new Coordinates(x, y));

                    } catch(Exception e) {
                        System.out.println(TextFormatter.toRed("Нужно ввести два числа!"));
                        i--;
                        continue;
                    }
                } else {
                    System.out.println(TextFormatter.toRed("Вы ввели в неверном формате!"));
                    i--;
                    continue;
                }
            } else if(nameValues[i].equals("price")) {
                System.out.println("Введите price:");
                el = ConsoleWorker.readLine();
                if(el == null) {
                    return null;
                }
                try {
                    Float price = Float.parseFloat(el);
                    if(price <= 0) {
                        System.out.println(TextFormatter.toRed("Число должно быть больше нуля!"));
                        i--;
                        continue;
                    }
                    ticket.setPrice(price);
                } catch(Exception e) {
                    System.out.println(TextFormatter.toRed("Нужно ввести число!"));
                    i--;
                    continue;
                }
            } else if(nameValues[i].equals("comment")) {
                System.out.println("Введите comment:");
                el = ConsoleWorker.readLine();
                if(el == null) {
                    continue;
                }
                ticket.setComment(el);
            } else if(nameValues[i].equals("type")) {
                System.out.println("Введите type из списка: VIP, USUAL, BUDGETARY, CHEAP");
                el = ConsoleWorker.readLine();
                if(el == null) {
                    return null;
                }
                try {
                    TicketType tType = TicketType.valueOf(el);
                    ticket.setType(tType);
                } catch(Exception e) {
                    System.out.println(TextFormatter.toRed("Нужно ввести что-то из списка!"));
                    i--;
                    continue;
                }
            } else if(nameValues[i].equals("venue")) {
                System.out.println("Введите venue в формате name;capacity");
                el = ConsoleWorker.readLine();
                if(el == null) {
                    return null;
                }
                if(el.indexOf(";") == el.lastIndexOf(";")) {
                    String[] venue = el.split(";");
                    try {
                        String name = venue[0];
                        Integer capacity = Integer.parseInt(venue[1]);
                        ticket.setVenue(new Venue(uId, name, capacity));
                    } catch (Exception e) {
                        System.out.println(TextFormatter.toRed("capacity должно быть числом!"));
                        i--;
                        continue;
                    }
                } else {
                    System.out.println(TextFormatter.toRed("Вы ввели в неверном формате!"));
                    i--;
                    continue;
                }
            }
        }
        ticket.setCreationDate(LocalDateTime.now());
        return ticket;
    }
}
