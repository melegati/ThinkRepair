public Object clone() throws CloneNotSupportedException {
    TimeSeries clone = (TimeSeries) super.clone();
    clone.data = (List) ObjectUtilities.deepClone(this.data); //Fix line
    return clone;
}