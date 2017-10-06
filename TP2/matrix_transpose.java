//each input value encodes a single row of the matrix
//the output should be composed of the columns of the matrix


map(Key k,string value,context &ctx){
  int col = 0;
  int row = key.get();
  for(string n:value.split(",")){
    ctx.write(new Key(col),to_string(row)+"\t"+n);
  }
}

reduce(Key k, string values){
  map<int,string> row;
  for(string t:values){
    string parts = text.split("\t");
    row.insert(make_pair(stoi(parts[0]),parts[1]));
  }
  for(k : row)
    printf(row[k]+'\n');
}
