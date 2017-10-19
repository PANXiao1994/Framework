import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class transpose {

    public static class Map extends Mapper<LongWritable, Text, LongWritable, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            long column = 0;
            long RowNum = key.get(); //get the value
            // get the elements of this line
            String [] nums = value.toString().split(",");
            for (String num : nums) {
                // column num as the new key
                context.write(new LongWritable(column), new Text(RowNum + "," + num));
                ++column;
            }
        }
    }

    public static class Reduce extends Reducer<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            TreeMap<Long, String> row = new TreeMap<Long, String>(); // values sorted by positions in input file
            for (Text text : values) {
                String[] newRow = text.toString().split(","); // size:2, (Rownum, value)
                row.put(Long.valueOf(newRow[0]), newRow[1]); // key value
            }
            String rowString = StringUtils.join(row.values(), ',');
            context.write(new Text(rowString), NullWritable.get()); // Write in
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        if (2 != args.length) {
            System.err.println("File path not provided");
            System.exit(1);
        }

        Job job = Job.getInstance(conf, "transpose");
        job.setJobName("matrix");
        job.setJarByClass(transpose.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Integer.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
