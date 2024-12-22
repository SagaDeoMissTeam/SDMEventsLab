package net.sixik.sdmeventslab.events;

import net.minecraft.util.RandomSource;
import net.sixik.sdmeventslab.events.function.EventFunction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChancableFunction<T> extends EventFunction {

    protected final Map<T, Double> contents;

    public ChancableFunction(Map<T, Double> contents) {
        this.contents = contents;
        if(this.contents == null)
            throw new RuntimeException("No contents provided for function " + getClass());
    }

    @Nullable
    public T getRandomElement() {
        RandomSource source = RandomSource.create();

        List<T> el = new ArrayList<>();
        for (Map.Entry<T, Double> v1 : contents.entrySet()) {
            if (v1.getValue() >= 100) {
                el.add(v1.getKey());
            }
        }

        if (!el.isEmpty()) {
            return el.get(source.nextInt(el.size()));
        }

        double sum = 0;
        for (Map.Entry<T, Double> value : contents.entrySet()) {
            sum += value.getValue();
        }

        double rand = source.nextDouble() * sum;
        double currentSum = 0;

        for (Map.Entry<T, Double> value : contents.entrySet()) {
            currentSum += value.getValue();
            if (rand <= currentSum) {
                return value.getKey();
            }
        }

        return null;
    }
}
