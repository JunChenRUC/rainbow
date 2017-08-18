package cn.edu.ruc.iir.rainbow.benchmark.cli;

import cn.edu.ruc.iir.rainbow.benchmark.generator.DataGenerator;
import cn.edu.ruc.iir.rainbow.benchmark.util.SysSettings;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.rainbow.benchmark.cli
 * @ClassName: Main
 * @Description: To start generating the datas by datasize
 * @author: Tao
 * @date: Create in 2017-07-31 8:21
 **/
public class Main
{

    public static void main(String args[])
    {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("Rainbow Benchmark")
                .defaultHelp(true)
                .description("Generate benchmark data by giving data-size, thread-num and directory options.");
        parser.addArgument("-s", "--data_size").required(true)
                .help("specify the size of data generated in MB");
        parser.addArgument("-t", "--thread_num").setDefault("4").required(true)
                .help("specify the number of threads used for data generation");
        parser.addArgument("-d", "--directory").required(true)
                .help("specify the directory of data template");
        Namespace ns = null;
        try
        {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e)
        {
            parser.handleError(e);
            System.out.println("Rainbow Benchmark (https://github.com/dbiir/rainbow/tree/master/rainbow-benchmark).");
            System.exit(1);
        }

        try
        {
            int dataSize = Integer.valueOf(ns.getString("data_size"));
            int threadNum = Integer.valueOf(ns.getString("thread_num"));
            String directory = ns.getString("directory");
            if (directory.endsWith("/"))
            {
                directory = directory.substring(0, directory.length() - 1);
            }
            if (dataSize > 0 && threadNum > 0 && directory != null)
            {
                SysSettings.TEMPLATE_DIRECTORY = directory;
                DataGenerator instance = DataGenerator.getInstance(threadNum);
                long startTime = System.currentTimeMillis();
                instance.genDataBySize(dataSize);
                long endTime = System.currentTimeMillis();
                System.out.println(dataSize + " MB data generated by " + threadNum + " threads in " + (endTime - startTime) / 1000 + "s.");
            } else
            {
                System.out.println("Please input the dataSize & threadNum & directory.");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}