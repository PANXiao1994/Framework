//each input value encodes a single row of the matrix
//the output should be composed of the columns of the matrix


map(Key k,string value,context &ctx){
  int col = 0;
  int row = key.get();
  for(string n:value.split(",")){
    ctx.write(new Key(col),to_string(row)+"\t"+n);
    col++;
  }
}

reduce(Key k, string values){
  map<int,string> row;
  for(string t:values){
    string newrow = text.split("\t");
    row.insert(make_pair(stoi(newrow[0]),newrow[1]));
  }
  for(k : row)
    printf(row[k]+'\n');
}
