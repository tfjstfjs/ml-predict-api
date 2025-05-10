package com.example;

import ai.onnxruntime.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.nio.FloatBuffer;

@Service
public class OnnxPredictor {
    private final OrtEnvironment env;
    private final OrtSession session;

    public OnnxPredictor() throws OrtException {
        env = OrtEnvironment.getEnvironment();
        session = env.createSession("models/user_behavior_predictor.onnx", new OrtSession.SessionOptions());
    }

    public Map<String, Object> predict(float[][][] inputData) throws OrtException {
        int batch = inputData.length;
        int seqLen = inputData[0].length;
        int dim = inputData[0][0].length;

        long[] shape = new long[] { batch, seqLen, dim };
        FloatBuffer buffer = FloatBuffer.allocate(batch * seqLen * dim);

        for (int b = 0; b < batch; b++) {
            for (int t = 0; t < seqLen; t++) {
                for (int d = 0; d < dim; d++) {
                    buffer.put(inputData[b][t][d]);
                }
            }
        }
        buffer.rewind();

        OnnxTensor inputTensor = OnnxTensor.createTensor(env, buffer, shape);

        OrtSession.Result result = session.run(Collections.singletonMap("input", inputTensor));
        float[][] output = (float[][]) result.get(0).getValue();

        int maxIdx = 0;
        for (int i = 1; i < output[0].length; i++) {
            if (output[0][i] > output[0][maxIdx])
                maxIdx = i;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("prediction", maxIdx);
        response.put("probabilities", output[0]);
        return response;
    }
}