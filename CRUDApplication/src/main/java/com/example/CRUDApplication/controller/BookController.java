package com.example.CRUDApplication.controller;



import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping ("/getallbooks/")
    public ResponseEntity<List<Book>> getallbooks(){
        try{
            List<Book> bookList= new ArrayList<>();
            bookRepo.findAll().forEach(bookList::add);

            if(bookList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("/getbookbyid/{id}")
    public ResponseEntity<Book> getbookbyID (@PathVariable Long id){
        Optional <Book> bookData= bookRepo.findById(id);

        if(bookData.isPresent()){
            return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addbook/")
    public ResponseEntity<Book> addbook(@RequestBody Book book){
        Book Bookobj =bookRepo.save(book);
        return new ResponseEntity<>(Bookobj,HttpStatus.OK);
    }

    @PostMapping("/Updatebookbyid/{id}")
    public ResponseEntity<Book> updatebookbyID(@PathVariable Long id, @RequestBody Book book){
    try{
       Optional<Book> oldBookData=bookRepo.findById(id);

       if(oldBookData.isPresent()){
        Book updatedBookData=oldBookData.get();
        updatedBookData.setTitle(book.getTitle());
        updatedBookData.setAuthor(book.getAuthor());

        Book Bookobj =bookRepo.save(updatedBookData);
        return new ResponseEntity<>(Bookobj, HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    @DeleteMapping ("/deletebookbyID/{id}")
    public ResponseEntity<Book> deletebookbyID(@PathVariable Long id){
        bookRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
