/*
 * Copyright (c) 2019 Petr Ptáček
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package cz.komix.temprec.domain;

import cz.komix.temprec.common.Constants;
import cz.komix.temprec.domain.Sample;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility for parsing and loading {@link Sample}
 * instances from resources.
 *
 * @author Petr Ptáček
 */
public class SampleLoader {

    /**
     * You are not allowed to create instance of this class.
     */
    private SampleLoader() { }

    /**
     * Load list of samples from resource.
     * Resource is dependent on reader in argument.
     *
     * @param reader
     *          resource reader
     *
     * @return  list of {@link Sample}.
     *          Never returns <tt>null</tt>.
     *
     * @throws IOException
     *          occures when there is an error in reading resource.
     */
    public static List<Sample> load(Reader reader) throws IOException {
        List<Sample> result = new ArrayList<>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(reader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Sample sample = parse(line);
                if (sample != null) {
                    result.add(sample);
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return result;
    }

    /**
     * Create file reader for reading text files.
     *
     * @param path
     *          path to text file
     *
     * @return  instance of file reader.
     *
     * @throws FileNotFoundException
     *          occurs if file or directory not found.
     */
    public static Reader createFileReader(String path) throws FileNotFoundException {
        return new FileReader(path);
    }

    /**
     * Parse request line into measurement sample object.
     * Request line should have {@value Constants#SAMPLE_INPUT_PATTERN}
     * pattern.
     *
     * @param request
     *          request line for parsing.
     *
     * @return  instance of {@link Sample} or <tt>null</tt>,
     *          if parsing failed.
     */
    public static Sample parse(String request) {
        if (request == null || !request.matches(Constants.SAMPLE_INPUT_PATTERN)) {
            return null;
        }

        String[] split = request.split(" ");

        String region = split[0];
        BigDecimal temperature = new BigDecimal(split[1]);

        return Sample.create(region, temperature);
    }
}