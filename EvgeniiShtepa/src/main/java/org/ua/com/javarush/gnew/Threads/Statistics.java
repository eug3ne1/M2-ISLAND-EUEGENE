package org.ua.com.javarush.gnew.Threads;

import org.ua.com.javarush.gnew.Annotations.AnimalsUnicode;
import org.ua.com.javarush.gnew.model.Animals.Intarfaces.Organism;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Statistics {
    private static Statistics INSTANCE;
    private Map<Class<? extends Organism>, Integer> animalsCount;

    private Statistics() {
        this.animalsCount = new ConcurrentHashMap<>();
    }

    public static Statistics getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Statistics();
        }
        return INSTANCE;
    }

    public synchronized void reset() {
        animalsCount.clear();
    }

    public synchronized void addToStatistic(Class<? extends Organism> clazz, int count) {
        animalsCount.merge(clazz, count, Integer::sum);
    }

    public synchronized void printStatistic() {
        System.out.println("=============Статистика==============");
        if (animalsCount.isEmpty()) {
            System.out.println("На острове нет животных");
        } else {
            for (Map.Entry<Class<? extends Organism>, Integer> entry : animalsCount.entrySet()) {
                String unicode = "♦️";
                if (!(entry.getKey().getAnnotation(AnimalsUnicode.class) == null)){
                    unicode = entry.getKey().getAnnotation(AnimalsUnicode.class).value();
                }
                System.out.println("Вид [" + entry.getKey() .getSimpleName() + unicode + "] Количество: " + entry.getValue());
            }
        }
        System.out.println("=====================================");

    }
}
