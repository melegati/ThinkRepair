public Object answer(InvocationOnMock invocation) {
    if (methodsGuru.isToString(invocation.getMethod())) {
        Object mock = invocation.getMock();
        MockName name = mockUtil.getMockName(mock);
        if (name.isDefault()) {
            return "Mock for " + mockUtil.getMockSettings(mock).getTypeToMock().getSimpleName() + ", hashCode: " + mock.hashCode();
        } else {
            return name.toString();
        }
    } else if (methodsGuru.isCompareToMethod(invocation.getMethod())) {
        //see issue 184.
        //mocks by default should return 0 if references are the same, otherwise some other value because they are not the same. Hence we return 1 (anything but 0 is good).
        //Only for compareTo() method by the Comparable interface
        Object[] args = invocation.getArguments();
        Object obj = args[0];
        if (obj == invocation.getMock()) {
            return 0; // Return 0 if objects are the same
        } else {
            return 1; // Return 1 if objects are not the same
        }
    }
    
    Class<?> returnType = invocation.getMethod().getReturnType();
    return returnValueFor(returnType);
}