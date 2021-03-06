package org.apfloat.internal;

import org.apfloat.spi.MatrixBuilder;
import org.apfloat.spi.MatrixStrategy;

/**
 * Creates matrix operations objects, for the
 * <code>long</code> type.
 *
 * @since 1.7.0
 * @version 1.7.0
 * @author Mikko Tommila
 */

public class LongMatrixBuilder
    implements MatrixBuilder
{
    /**
     * Default constructor.
     */

    public LongMatrixBuilder()
    {
    }

    public MatrixStrategy createMatrix()
    {
        return LongMatrixBuilder.matrixStrategy;
    }

    private static MatrixStrategy matrixStrategy = new LongMatrixStrategy();
}
