// File: DataExtractor.java
package id.ac.ui.cs.advprog.adproggameshop.service;

import java.util.List;

public interface DataExtractor<T> {
    List<T> extractData();
}
