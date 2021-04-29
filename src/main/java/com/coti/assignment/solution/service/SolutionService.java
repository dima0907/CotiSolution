package com.coti.assignment.solution.service;

import com.coti.assignment.solution.persistence.domain.NumberedArrayEntity;
import com.coti.assignment.solution.persistence.repository.INumberedArrayEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SolutionService
{
    private static final Logger LOGGER = LoggerFactory.getLogger("Solution.logger");
    private static List<Long> intervalArray = Collections.synchronizedList(new ArrayList<>());
    private static String NOT_VALID_INPUT_RESPONSE = "Not a valid input was passed , please revise and try once again.";
    private long sumOfNumbers = 0;

    @Autowired
    private INumberedArrayEntity numberedArray;

    public ResponseEntity<String> insertNumber(String num)
    {
        long currentSum;
        long numValue = stringToLong(num);

        if(negativeInputValidation(numValue))
        {
            return ResponseEntity.badRequest().body(NOT_VALID_INPUT_RESPONSE);
        }

        currentSum = getSumAfterInsert(numValue);

        String response = "Sum of numbers that were logged to the database: " + currentSum + "\n" +
                "The sum of all values multiplied by the input value: " + (Math.min(currentSum * numValue, Long.MAX_VALUE));
        LOGGER.info(response);

        return ResponseEntity.ok(response);
    }

    private boolean negativeInputValidation(long input)
    {
        if(input<0)
        {
            LOGGER.error("SolutionService::inputValidation - negative number was passed to the function, value: " + input);
            return true;
        }
        return false;
    }

    private long stringToLong(String number)
    {
        long numValue;
        try
        {
            numValue = Long.parseLong(number);
        } catch (NumberFormatException e)
        {
            LOGGER.error("SolutionService::stringToLong - Error occurred during number conversion, value: " + number
                    + " Error: ", e);
            return -1;
        }
        return numValue;
    }

    private int stringToInt(String number)
    {
        int numValue;
        try
        {
            numValue = Integer.parseInt(number);
        } catch (NumberFormatException e)
        {
            LOGGER.error("SolutionService::stringToInt - Error occurred during number conversion, value: " + number
                    + " Error: ", e);
            return -1;
        }
        return numValue;
    }

    private synchronized long getSumAfterInsert(long numValue)
    {
        NumberedArrayEntity newNumber = new NumberedArrayEntity(numValue);
        numberedArray.save(newNumber);
        intervalArray.add(numValue);
        if (sumOfNumbers+numValue<=Long.MAX_VALUE)
        {
            sumOfNumbers += numValue;
        }
        else {
            return -1;
        }

        return sumOfNumbers;
    }

    public ResponseEntity<String> removeIndex(String dbIndex)
    {
        int index = stringToInt(dbIndex);

        if(negativeInputValidation(index) || index>intervalArray.size())
        {
            LOGGER.error("SolutionService::removeIndex - Index that was given did not passed the validation."
                    + "\n" + "Index value: " + index);
            return ResponseEntity.badRequest().body(NOT_VALID_INPUT_RESPONSE);
        }

        updateAllWithIndexToDelete(index);

        return ResponseEntity.ok("The index " + dbIndex + " was removed successfully.");
    }

    private synchronized void updateAllWithIndexToDelete(int index)
    {
        int lastIndex = intervalArray.size();
        NumberedArrayEntity lastEntry = numberedArray.findById(lastIndex);
        NumberedArrayEntity givenIndexToRemove = numberedArray.findById(index + 1);

        if(lastEntry == null || givenIndexToRemove == null)
        {
            LOGGER.error("SolutionService::updateAllWithIndexToDelete - Entries in DB not found for last: "
                    + lastEntry + " and for given index entry: " + givenIndexToRemove);
            return;
        }

        long lastEntryValue = lastEntry.getValue();
        long givenIndexToRemoveValue = givenIndexToRemove.getValue();
        lastEntry.setValue(givenIndexToRemoveValue);
        givenIndexToRemove.setValue(lastEntryValue);

        numberedArray.save(lastEntry);
        numberedArray.save(givenIndexToRemove);

        long REMOVED_INDEX = -1;
        intervalArray.set(index, REMOVED_INDEX);
    }

    public ResponseEntity<List<Long>> returnIndexes(String[] strIndexes)
    {
        int[] numIndexes = new int[strIndexes.length];
        int index = 0;
        List<Long> response = new ArrayList<>();

        for(String num : strIndexes)
        {
            int indexValue = stringToInt(num);
            if(negativeInputValidation(indexValue) || indexValue>intervalArray.size() -1)
            {
                LOGGER.error("SolutionService::returnIndexes - Index that was given did not passed the validation."
                        + "\n" + "Index value: " + indexValue);
                numIndexes[index] = -1;
                index ++;
                continue;
            }

            numIndexes[index] = indexValue;
            index ++;
        }

        synchronized (intervalArray)
        {
            for (int num : numIndexes)
            {
                if (num != -1)
                {
                    response.add(intervalArray.get(num));
                }
            }
        }

        return ResponseEntity.ok(response);
    }
}
