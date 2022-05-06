package com.census;
import java.io.IOException;

import org.junit.Test;

import junit.framework.Assert;

public class StateCensusAnalyserTest {
    public static final String STATE_CODE_CSV_PATH = "src/resources/StateCode.csv";
    public static final String STATE_CENSUS_CSV_PATH = "src/resources/StateCensusData.csv";
    public static final String WRONG_FILE = "/useless.txt";

    @Test
    public void GivenTheStateCodesCsvFile_IfHasCorrectNumberOfRecords_ShouldReturnTrue() throws IOException {
        try {
            int count = StateCensusAnalyser.openCsvBuilder(STATE_CENSUS_CSV_PATH, StateCensus.class);
//            System.out.println(count);
            Assert.assertEquals(29, count);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GivenTheStateCensusCsvFile_IfDoesntExist_ShouldThrowCensusAnalyserException() throws IOException {
        try {
            int count = StateCensusAnalyser.openCsvBuilder(WRONG_FILE, StateCensus.class);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void GivenTheStateCensusCsvFile_WhenCorrect_ButFileExtensionIncorrect_ShouldThrowCensusAnalyserException() throws IOException {
        try {
            int count = StateCensusAnalyser.openCsvBuilder(STATE_CODE_CSV_PATH, StateCensus.class);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE, e.type);
        }
    }

    @Test
    public void GivenTheStateCensusCSVFile_WhenCorrect_ButDelimiterIncorrect_ReturnsCensusAnalyserException() throws IOException {
        try {
            int count = StateCensusAnalyser.openCsvBuilder(STATE_CENSUS_CSV_PATH, StateCensus.class);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.DELIMITER_ISSUE, e.type);

        }
    }

    @Test
    public void whenCorrectCensusCSVFile_ButHeaderIncorrect_ShouldReturnFalse() throws IOException {
        try {
            int count = StateCensusAnalyser.openCsvBuilder(STATE_CENSUS_CSV_PATH, StateCensus.class);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
            Assert.assertEquals(CensusAnalyserException.CensusExceptionType.INCORRECT_DATA_ISSUE, e.type);
        }
    }
}