package com.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
@RequestMapping("/predict_behavior")
public class PredictController {

    private final OnnxPredictor predictor;

    @Autowired
    public PredictController(OnnxPredictor predictor) {
        this.predictor = predictor;
    }

    @PostMapping
    public Map<String, Object> predict(@RequestBody Map<String, Object> payload) throws Exception {
        List<List<List<Number>>> data = (List<List<List<Number>>>) payload.get("sequence");
        float[][][] input = new float[1][data.get(0).size()][data.get(0).get(0).size()];
        for (int i = 0; i < data.get(0).size(); i++) {
            for (int j = 0; j < data.get(0).get(i).size(); j++) {
                input[0][i][j] = data.get(0).get(i).get(j).floatValue();
            }
        }
        return predictor.predict(input);
    }
}
