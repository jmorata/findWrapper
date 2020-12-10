package com.roche.cache.wrapper;

import com.roche.cache.wrapper.service.FindWrapperService;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (checkNOValidParams(args)) return;

        FindWrapperService findWrapperService = new FindWrapperService(new File(args[0]), args[1]);
        findWrapperService.execute();
    }

    private static boolean checkNOValidParams(String[] args) {
        if (args.length < 2) {
            System.out.println("directory | searchTerm");
            return true;
        }
        return false;
    }

}

