//package com.example.demo.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BaseServiceImpl<T> implements BaseService<T> {
//
//    @Autowired
//    private JpaRepository<T, Long> repository;
//
//    @Override
//    public List<T> getAll() {
//        return repository.findAll();
//    }
//
//    @Override
//    public T getById(Long id) {
//        return repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found with id " + id));
//    }
//
//    @Override
//    public T create(T obj) {
//        return repository.save(obj);
//    }
//
//    @Override
//    public T update(Long id, T obj) {
//        if (!repository.existsById(id)) {
//            throw new RuntimeException("Entity not found with id " + id);
//        }
//        return repository.save(obj);
//    }
//
//    @Override
//    public void delete(Long id) {
//        if (!repository.existsById(id)) {
//            throw new RuntimeException("Entity not found with id " + id);
//        }
//        repository.deleteById(id);
//    }
//}
