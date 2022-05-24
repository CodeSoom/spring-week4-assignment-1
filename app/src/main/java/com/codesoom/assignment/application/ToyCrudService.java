package com.codesoom.assignment.application;

import com.codesoom.assignment.interfaces.CrudService;
import com.codesoom.assignment.interfaces.Product;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ToyCrudService implements CrudService {

    @Override
    public List<Product> showAll() {
        return new LinkedList<>();
    }
}
