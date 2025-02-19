JSType resolveInternal(ErrorReporter t, StaticScope<JSType> scope) {
  setResolvedTypeInternal(this);

  call = (ArrowType) safeResolve(call, t, scope);
  prototype = (FunctionPrototypeType) safeResolve(prototype, t, scope);

  // Check result of safeResolve() before casting to ObjectType
  JSType typeOfThisResolved = safeResolve(typeOfThis, t, scope); 
  if (typeOfThisResolved instanceof ObjectType) {
      typeOfThis = (ObjectType) typeOfThisResolved;
  }

  boolean changed = false;
  ImmutableList.Builder<ObjectType> resolvedInterfaces =
      ImmutableList.builder();
  for (ObjectType iface : implementedInterfaces) {
    ObjectType resolvedIface = (ObjectType) iface.resolve(t, scope);
    resolvedInterfaces.add(resolvedIface);
    changed |= (resolvedIface != iface);
  }
  if (changed) {
    implementedInterfaces = resolvedInterfaces.build();
  }

  if (subTypes != null) {
    for (int i = 0; i < subTypes.size(); i++) {
      subTypes.set(i, (FunctionType) subTypes.get(i).resolve(t, scope));
    }
  }

  return super.resolveInternal(t, scope);
}