package com.indiancensus;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
public class StateCensusAnalyser {
    public StateCensusAnalyser() {
    }
    public static <T>  int openCsvBuilder(String csvFilePath, Object myClass) throws CensusAnalyserException {
        int counter = 0;
        try {
            Iterator<Object> myIterator = getIterator(csvFilePath, myClass);
            while ( myIterator.hasNext() ) {
                counter++;
                Object myObj = myIterator.next();
            }
        } catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.DELIMITER_ISSUE,
                    "might be some error related to delimiter at record no. : " +(counter+1));
        }
        return counter;
    }
    public static Iterator<Object> getIterator(String csvFilePath, Object myClass) throws CensusAnalyserException {
        Reader reader = null;
        CsvToBean<Object> csvToBean;
        try {
            reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<Object> csvToBeanBuilder = new CsvToBeanBuilder(reader);
            csvToBeanBuilder.withType((Class) myClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            csvToBean = csvToBeanBuilder.build();
//            csvToBean = new CsvToBeanBuilder(reader)
//                    .withType((Class) myClass)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build();
            return csvToBean.iterator();
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.NO_SUCH_FILE,
                    "no such file exists. Please enter correct file");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE,
                    "delimiter error at line 1 OR might be some error " +
                            "related to headers or incorrect extension / input file ");
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.CensusExceptionType.SOME_OTHER_IO_EXCEPTION,
                    "Some other IO related exception");
        }
    }
}