package com;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * Unit factories for simple App.
 */
public class ReactorTest {
    private static List<String> words = Arrays.asList(
            "the",
            "quick",
            "brown",
            "fox",
            "jumped",
            "over",
            "the",
            "lazy",
            "dog"
    );

    /**
     * Rigorous Test :-)
     */
    @Test
    public void testCreate() {
        Flux<String> flux = Flux.fromIterable(words);
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4);
        flux.subscribe(System.out::println);
        integerFlux.subscribe(System.out::println);
    }

    @Test
    public void findingMissingLetter() {
        Flux<String> flux = Flux.fromIterable(words)
                .flatMap(s -> Flux.fromArray(s.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE), (s, integer) -> String.format("%d : %s", integer, s));
        flux.subscribe(System.out::println);

    }

    @Test
    public void firstEmitting() {
        Mono<String> a = Mono.just("oops I'm late")
                .delaySubscriptionMillis(450);
        Flux<String> b = Flux.just("let's get", "the party", "started")
                .delayMillis(400);

        Flux.firstEmitting(a, b)
                .toIterable()
                .forEach(System.out::println);

        Mono.just("foo").subscribe(System.out::println);
        Mono.create(sink -> {
            sink.success("create MonoSink");
        }).subscribe(o -> {
            System.out.println(o);
        });


    }


}
