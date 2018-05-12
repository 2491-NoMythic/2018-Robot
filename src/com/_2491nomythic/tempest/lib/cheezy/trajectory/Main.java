package com._2491nomythic.tempest.lib.cheezy.trajectory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com._2491nomythic.tempest.lib.cheezytrajectory.io.JavaSerializer;
import com._2491nomythic.tempest.lib.cheezytrajectory.io.JavaStringSerializer;
import com._2491nomythic.tempest.lib.cheezytrajectory.io.TextFileSerializer;

/**
 *
 * @author Jared341
 */
public class Main {
  public static String joinPath(String path1, String path2)
  {
      File file1 = new File(path1);
      File file2 = new File(file1, path2);
      return file2.getPath();
  }
  
  private static boolean writeFile(String path, String data) 
  {
    try {
      File file = new File(path);

      // if file doesnt exists, then create it
      if (!file.exists()) {
          file.createNewFile();
      }

      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(data);
      bw.close();
    } catch (IOException e) {
      return false;
    }
    
    return true;
  }
  
  public static void main(String[] args)
  {
    String directory = "../FRC-2014/paths";
    if (args.length >= 1) {
      directory = args[0];
    }
    
    TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
    config.dt = .01;
    config.max_acc = 10.0;
    config.max_jerk = 60.0;
    config.max_vel = 15.0;
    
    final double kWheelbaseWidth = 23/12;
    {
      config.dt = .1;
      config.max_acc = 15.0;
      config.max_jerk = 50.0;
      config.max_vel = 12.421;
      // Path name must be a valid Java class name.
      final String path_name = "TEST PATH";
      
      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);
      /*p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(7.0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(9.0, 1.0, Math.PI / 4.0));
      p.addWaypoint(new WaypointSequence.Waypoint(10.5, 7.0, Math.PI / 2.0));
      */
      p.addWaypoint(new WaypointSequence.Waypoint(0.0, 0.0, 0.0));
      p.addWaypoint(new WaypointSequence.Waypoint(23.5/12, -26.0/12, 0.0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    }
  }
