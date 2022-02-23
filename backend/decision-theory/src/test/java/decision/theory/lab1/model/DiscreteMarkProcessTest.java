package decision.theory.lab1.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import decision.theory.lab1.interfaces.model.IMarkProcessResult;
import decision.theory.lab1.service.DiscreteMarkProcessService;
import decision.theory.lab1.service.RandomService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteMarkProcessTest {

    @Test
    public void simpleTest() throws JsonProcessingException {
        var probabilityData = List.of(
                new double[][] {
                        {1.00, 0.00},
                        {0.10, 0.90}
                },
                new double[][] {
                        {1.00, 0.00},
                        {0.33, 0.67}
                },
                new double[][] {
                        {1.00, 0.00},
                        {0.33, 0.67}
                }
        );
        var valueData = List.of(
                new double[][] {
                        {8.68, 0.00},
                        {2.43, 3.29}
                },
                new double[][] {
                        {16.83, 0.00},
                        {14.11, 7.63}
                },
                new double[][] {
                        {03.23, 0.00},
                        {10.07, 7.86}
                }
        );
        IMarkProcessResult result = new DiscreteMarkProcessService(new RandomService()).calculate(probabilityData, valueData, 3);
        System.out.println(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(result));
        assertEquals(
                new TreeMap<>() {
                    {
                        put(0, new TreeMap<Integer, Double>() {
                            {
                                put(0, 0.0);
                                put(1, 0.0);
                            }
                        });
                        put(1, new TreeMap<Integer, Double>() {
                            {
                                put(0, 16.83);
                                put(1, 9.77);
                            }
                        });
                        put(2, new TreeMap<>() {
                            {
                                put(0, 33.66);
                                put(1, 21.87);
                            }
                        });
                        put(3, new TreeMap<>() {
                            {
                                put(0, 50.49);
                                put(1, 35.53);
                            }
                        });
                    }
                },
                result.getValueResultPerStep()
        );
    }

}
