package utils.builder;

import org.mockito.exceptions.base.MockitoAssertionError;

/**
 * Created by Administrator on 2017/2/11.
 */

public class UncleMockException extends MockitoAssertionError {
    public UncleMockException(final String message) {
        super(message);
    }
}
