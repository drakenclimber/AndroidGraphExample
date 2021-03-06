/*******************************************************************************
*
*  Copyright 2011-2012 Drakenclimber, LLC.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*
********************************************************************************
* Revision information.  DO NOT MODIFY.  This is auto-generated by the SCM.
*
* $Date: 2012-01-15 15:09:58 -0600 (Sun, 15 Jan 2012) $
* $Revision: 34 $
*
*******************************************************************************/
/**
 * Doxygen comment block
 * @file
 *
 * @brief   Class for managing an array of graph data
 *
 */
package com.drakenclimber.graph;

import java.util.ArrayList;

public class GraphData {
    private ArrayList<Float> values;
    private Float minValue;
    private Float maxValue;
    private int graphColor;
    private int maxValueCount;
    private float maxScale;
    private float minScale;

    public GraphData(float[] initValues, int color, int maxValueCount) {
        resetValues();

        if (initValues != null) {
            for (float f : initValues) {
                appendValue(f);
            }
        }

        graphColor = color;

        if (0 == maxValueCount) {
            this.maxValueCount = Integer.MAX_VALUE;
        } else {
            this.maxValueCount = maxValueCount;
        }
    }   /* GraphData() */

    /** reset the graph and clear out all values */
    public void resetValues() {
        values = new ArrayList<Float>();
        minValue = Float.MAX_VALUE; /*
                                     * set to MAX since values will certainly be
                                     * less than this
                                     */
        maxValue = Float.MIN_VALUE; /*
                                     * set to MIN since values will certainly be
                                     * greater than this
                                     */
    }   /* resetValues() */

    /** add a value to the data set */
    public void appendValue(float val) {
        Float classVal = new Float(val);

        /*
         * if the current data size is too large, remove the first element
         * before adding new one
         */
        if (values.size() >= maxValueCount) {
            /*
             * if the max Value is the one to be removed, scan for next max
             * value
             */
            if (maxValue.equals(values.get(0))) {
                int maxIndex = 1;
                for (int i = 2; i < values.size(); i++) {
                    if (values.get(i) > values.get(maxIndex)) {
                        maxIndex = i;
                    }
                }
                setMaxY(values.get(maxIndex));
            }
            /*
             * if the min Value is the one to be removed, scan for next min
             * value
             */
            if (minValue.equals(values.get(0))) {
                int minIndex = 1;
                for (int i = 2; i < values.size(); i++) {
                    if (values.get(i) < values.get(minIndex)) {
                        minIndex = i;
                    }
                }
                setMinY(values.get(minIndex));
            }
            values.remove(0);
        }

        if (val > maxValue.floatValue()) {
            setMaxY(classVal);
        }

        if (val < minValue.floatValue()) {
            setMinY(classVal);
        }

        values.add(classVal);
    }   /* appendValue() */

    /** set max Y scale */
    private void setMaxY(Float maxY) {
        double orderOfMagnitude;

        maxValue = maxY;

        if (maxValue.floatValue() == 0f) {
            maxScale = 0f;
        } else {
            /* figure out a good max value for the Y scale */
            orderOfMagnitude = Math.pow(10,
                    Math.ceil(Math.log10(Math.abs(maxValue.floatValue()))) - 1);
            maxScale = (float) (Math.ceil(maxValue.floatValue()
                    / orderOfMagnitude) * orderOfMagnitude);
        }
    }   /* setMaxY() */

    /** set min Y scale */
    private void setMinY(Float minY) {
        double orderOfMagnitude;

        minValue = minY;

        if (minValue.floatValue() == 0f) {
            minScale = 0f;
        } else {
            /* figure out a good min value for the Y scale */
            orderOfMagnitude = Math.pow(10,
                    Math.ceil(Math.log10(Math.abs(minValue.floatValue()))) - 1);
            minScale = (float) (Math.floor(minValue.floatValue()
                    / orderOfMagnitude) * orderOfMagnitude);
        }
    }   /* setMinY() */

    /** set maximum number of values that the graph can show */
    public void setMaxValueCount(int maxValueCount) {
        this.maxValueCount = maxValueCount;
        while (values.size() > maxValueCount) {
            values.remove(0);
        }
    }   /* setMaxValueCount() */

    /** set the data set color */
    public void setColor(int color) {
        graphColor = color;
    }   /* setColor() */

    /** get the data set color */
    public int getColor() {
        return graphColor;
    }   /* getColor() */

    /**
     * get the computed min scale value from the data. used for autoscale
     * plotting.
     */
    public float getMaxScale() {
        return maxScale;
    }   /* getMaxScale() */

    /**
     * get the computed min scale value from the data. used for autoscale
     * plotting.
     */
    public float getMinScale() {
        return minScale;
    }   /* getMinScale() */

    /** get list of values */
    public ArrayList<Float> getValues() {
        return values;
    }   /* getValues() */
}   /* GraphData class */
