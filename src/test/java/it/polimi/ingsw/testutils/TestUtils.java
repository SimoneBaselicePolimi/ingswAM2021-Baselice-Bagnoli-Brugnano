package it.polimi.ingsw.testutils;

import it.polimi.ingsw.server.model.gameitems.IdentifiableItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

public class TestUtils {

    public static <T> List<T> generateListOfMock(Class<T> objectType, int numberOfObject){
        List<T> list = new ArrayList<>();
        for (int i = 0; i < numberOfObject; i++){
            list.add(mock(objectType));
        }
        return list;
    }

    public static <T extends IdentifiableItem> List<T> generateListOfMockWithID(Class<T> objectType, int numberOfObject){
        List<T> list = new ArrayList<>();
        for (int i = 0; i < numberOfObject; i++){
            T mock = mock(objectType);
            lenient().when(mock.getItemId()).thenReturn("mock_ID_" + i);
            list.add(mock);
        }
        return list;
    }

    public static <T> Set<T> generateSetOfMock(Class<T> objectType, int numberOfObject){
        Set<T> set = new HashSet<>();
        for (int i = 0; i < numberOfObject; i++){
            set.add(mock(objectType));
        }
        return set;
    }

    public static <T extends IdentifiableItem> Set<T> generateSetOfMockWithID(Class<T> objectType, int numberOfObject){
        Set<T> set = new HashSet<>();
        for (int i = 0; i < numberOfObject; i++){
            T mock = mock(objectType);
            lenient().when(mock.getItemId()).thenReturn("mock_ID_" + i);
            set.add(mock);
        }
        return set;
    }
}
