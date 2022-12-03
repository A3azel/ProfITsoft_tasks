package profITsoft.lectures5_6.task1;


import profITsoft.lectures5_6.task1.entity.ViolationJSON;
import profITsoft.lectures5_6.task1.entity.ViolationXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MultithreadedConvertor{


    public static void test(String dirPath) throws ExecutionException, InterruptedException {
        File jsonDirectory = new File(dirPath);
        File[] filesList = jsonDirectory.listFiles();
        if (filesList == null) {
            throw new IllegalArgumentException();
        }
        ExecutorService service = Executors.newFixedThreadPool(8);
        /*List<ViolationJSON> allViolations = Arrays.stream(filesList)
                .map(x -> CompletableFuture.supplyAsync(() -> ViolationConvertor.parseJSON(x),service))
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        service.shutdown();*/

        /*ExecutorService es = Executors.newFixedThreadPool(4);
        CompletableFuture<?>[] futures = Arrays.stream(filesList)
                .map(task -> CompletableFuture.runAsync(() -> ViolationConvertor.parseJSON(task), es))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        es.shutdown();*/

        /*List<ViolationXML> violationXMLS = ViolationConvertor.creatingAndSortingStatistics(allViolations).stream()
                .map(x -> new ViolationXML(x.getViolationType(),x.getFineAmount()))
                .collect(Collectors.toList());

        try {
            ViolationConvertor.createXML(violationXMLS,"result");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }*/

        //List<ViolationJSON> allViolations = new ArrayList<>();
        /*for (File selectedFile: filesList) {
            CompletableFuture<List<ViolationJSON>> localViolations = CompletableFuture.supplyAsync(()->
                    ViolationConvertor.parseJSON(selectedFile),service);
            localViolations.thenAccept(allViolations::addAll);
            //System.out.println(allViolations);
            //allViolations.addAll(localViolations.get());
        }*/


        // !!!

        /*for (File selectedFile : filesList) {
            CompletableFuture<List<ViolationJSON>> localViolations = CompletableFuture.supplyAsync(() -> {
                //executionThread();
                return ViolationConvertor.parseJSON(selectedFile);
            }, service);
            localViolations.thenApply(res -> {
                System.out.println(res.size());
                allViolations.addAll(res);
                System.out.println(allViolations.size());
                return res;
            });
            //localViolations.thenApply(allViolations::addAll);
            //localViolations.thenAccept(allViolations::addAll);
        }
        service.shutdown();*/

        //?
        /*CompletableFuture<Void> completableFuturesVoid;
        completableFuturesVoid = Arrays.stream(filesList)
                .map(x -> CompletableFuture.supplyAsync(() -> ViolationConvertor.parseJSON(x),service))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::allOf)

                .toArray(new CompletableFuture[0]);*/


        //Work
        List<CompletableFuture<List<ViolationJSON>>> completableFutures;

        completableFutures = Arrays.stream(filesList)
                .map(x -> CompletableFuture.supplyAsync(() -> ViolationConvertor.parseJSON(x),service))
                .collect(Collectors.toList());

        /*for (File selectedFile : filesList) {
            CompletableFuture<List<ViolationJSON>> completableFuture = CompletableFuture.supplyAsync(() -> ViolationConvertor.parseJSON(selectedFile), service);
            completableFutures.add(completableFuture);
        }*/

        service.shutdown();

        CompletableFuture<Void> all = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));

        CompletableFuture<List<ViolationJSON>> allPageContentsFuture = all.thenApply(v -> completableFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
        List<ViolationXML> violationXMLS = ViolationConvertor.creatingAndSortingStatistics(allPageContentsFuture.get()).stream()
                .map(x -> new ViolationXML(x.getViolationType(), x.getFineAmount()))
                .collect(Collectors.toList());
        try {
            ViolationConvertor.createXML(violationXMLS, "result");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        //


        /*List<ViolationXML> violationXMLS = ViolationConvertor.creatingAndSortingStatistics(allViolations).stream()
                .map(x -> new ViolationXML(x.getViolationType(), x.getFineAmount()))
                .collect(Collectors.toList());
        try {
            ViolationConvertor.createXML(violationXMLS, "result");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }*/
    }

    public static void executionThread() {
        System.out.println("Thread execution - " + Thread.currentThread().getName());
    }

}
