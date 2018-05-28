package com.ekart.transport.core.utils;

import com.ekart.transport.core.enums.CoreErrors;
import com.ekart.transport.core.enums.MeasureUnit;
import com.ekart.transport.core.exception.ServiceException;

import java.math.BigDecimal;

/**
 * Created by ankush.a on 05/12/17.
 */
public class ConversionMetrics {

    private static final double GRAM_TO_KG = 1.0 / 1000.0;
    private static final double CUBIC_CM_TO_CUBIC_METRE = 1.0 / (100.0 * 100.0 * 100.0);
    private static final double CUBIC_FEET_TO_CUBIC_METRE = 0.0283168;
    private static final double CUBIC_INCH_TO_CUBIC_METRE = 1.0 / 61023.7;
    private static final double CM_TO_METRE = 0.01;
    private static final double FEET_TO_METRE = 0.3048;
    private static final double INCH_TO_METRE = 0.0254;
    private static final double PAISA_TO_RUPEE = 0.01;
    private static final int SCALE = 5;

    public static double weightInKG(double weight, MeasureUnit measureUnit) throws ServiceException {
        double value = 0.0;

        if (MeasureUnit.KG.equals(measureUnit))
            value = weight;

        else if (MeasureUnit.GRAM.equals(measureUnit))
            value =  weight * GRAM_TO_KG;

        else
            throw new ServiceException(CoreErrors.INVALID_WEIGHT_UNIT);

        return BigDecimal.valueOf(value).setScale(SCALE,BigDecimal.ROUND_UP).doubleValue();
    }

    public static double volumeInCubicMetre(double volume, MeasureUnit measureUnit) throws ServiceException {
        double value = 0.0;

        if (MeasureUnit.CUBIC_METRE.equals(measureUnit))
            value = volume;

        else if (MeasureUnit.CUBIC_CM.equals(measureUnit))
            value = volume * CUBIC_CM_TO_CUBIC_METRE;

        else if (MeasureUnit.CUBIC_FEET.equals(measureUnit))
            value = volume * CUBIC_FEET_TO_CUBIC_METRE;

        else if (MeasureUnit.CUBIC_INCH.equals(measureUnit))
            value = volume * CUBIC_INCH_TO_CUBIC_METRE;

        else
            throw new ServiceException(CoreErrors.INVALID_VOLUME_UNIT);

        return BigDecimal.valueOf(value).setScale(SCALE,BigDecimal.ROUND_UP).doubleValue();
    }

    public static double dimensionInMetre(double dimension, MeasureUnit measureUnit) throws ServiceException {
        double value = 0.0;

        if (MeasureUnit.METRE.equals(measureUnit))
            value = dimension;

        else if (MeasureUnit.CM.equals(measureUnit))
            value = dimension * CM_TO_METRE;

        else if (MeasureUnit.FEET.equals(measureUnit))
            value = dimension * FEET_TO_METRE;

        else if (MeasureUnit.INCH.equals(measureUnit))
            value = dimension * INCH_TO_METRE;

        else
            throw new ServiceException(CoreErrors.INVALID_DIMENSION_UNIT);

        return BigDecimal.valueOf(value).setScale(SCALE,BigDecimal.ROUND_UP).doubleValue();
    }
}
