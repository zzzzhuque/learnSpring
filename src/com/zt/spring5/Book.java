package com.zt.spring5;

/**
 * 演示使用set方法进行属性注入
 */
public class Book {
    private String bookName;
    private String bookAuthor;
    private String authorAddress;

    // set方法注入
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setAuthorAddress(String authorAddress) {
        this.authorAddress = authorAddress;
    }

    public void printBookInfo() {
        System.out.println(bookName +":" + bookAuthor + ":" + authorAddress);
    }
}
