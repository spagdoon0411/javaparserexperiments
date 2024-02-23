package org.example;

import java.util.Objects;

/***
 * Holds information for a single static call.
 */
public class StaticCallInfo {

    public StaticCallInfo(String methodName, String callingClassName) {
        this.callingClassName = callingClassName;
        this.methodName = methodName;
    }

    public String callingClassName;
    public String methodName;

    @Override
    public boolean equals(Object other) {

        if(!(other instanceof StaticCallInfo))
            return false;

        return Objects.equals(this.callingClassName, ((StaticCallInfo)other).callingClassName)
                && Objects.equals(this.methodName, ((StaticCallInfo)other).methodName);
    }

    @Override
    public String toString() {
        return "Static method " + this.methodName + " has calling class " + this.callingClassName;
    }
}
