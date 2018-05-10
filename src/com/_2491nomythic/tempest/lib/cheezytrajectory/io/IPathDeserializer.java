package com._2491nomythic.tempest.lib.cheezytrajectory.io;

import com._2491nomythic.tempest.lib.cheezy.trajectory.Path;

/**
 * Interface for methods that deserializes a Path or Trajectory.
 * 
 * @author Jared341
 */
public interface IPathDeserializer {
  
  public Path deserialize(String serialized);
}
