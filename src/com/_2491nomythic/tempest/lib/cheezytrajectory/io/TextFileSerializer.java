package com._2491nomythic.tempest.lib.cheezytrajectory.io;

import com._2491nomythic.tempest.lib.cheezy.trajectory.Path;
import com._2491nomythic.tempest.lib.cheezy.trajectory.Trajectory;
import com._2491nomythic.tempest.lib.cheezy.trajectory.Trajectory.Segment;

/**
 * Serializes a Path to a simple space and CR separated text file.
 * 
 * @author Jared341
 */
public class TextFileSerializer implements IPathSerializer {

  /**
   * Format:
   *   PathName
   *   NumSegments
   *   LeftSegment1
   *   ...
   *   LeftSegmentN
   *   RightSegment1
   *   ...
   *   RightSegmentN
   * 
   * Each segment is in the format:
   *   pos vel acc jerk heading dt x y
   * 
   * @param path The path to serialize.
   * @return A string representation.
   */
	
	public enum RETURNTYPE{
		XVALUE,
		YVALUE,
		VELOCITY,
		DT,
		DISTANCE,
		JERK,
		HEADINGR,
		HEADINGD,
		ACCELERATION,
		PATHDIRECT
	}
	
  public String serialize(Path path) {
    String content = path.getName() + "\n";
    path.goLeft();
    content += "THIS PATH RUNS IN " + path.getLeftWheelTrajectory().getNumSegments() * .1 + " SECONDS" + "\n";
    content += "LEFT_VELOCITY{";
    content += serializeTrajectory(path.getLeftWheelTrajectory(), RETURNTYPE.VELOCITY);
    content += "};\nRIGHT_VELOCITY{";
    content += serializeTrajectory(path.getRightWheelTrajectory(), RETURNTYPE.VELOCITY);
    content += "};\nHEADING_IN_RADIANS{";
    content += serializeTrajectory(path.getLeftWheelTrajectory(), RETURNTYPE.HEADINGR);
    content += "};\nHEADING_IN_DEGREES{";
    content += serializeTrajectory(path.getLeftWheelTrajectory(), RETURNTYPE.HEADINGD);
    content += "};\nX_POS_LEFT_SIDE{";
    content += serializeTrajectory(path.getLeftWheelTrajectory(), RETURNTYPE.XVALUE);
    content += "};\nY_POS_LEFT_SIDE{";
    content += serializeTrajectory(path.getLeftWheelTrajectory(), RETURNTYPE.YVALUE);
    content += "};\nX_POS_RIGHT_SIDE{";
    content += serializeTrajectory(path.getRightWheelTrajectory(), RETURNTYPE.XVALUE);
    content += "};\nY_POS_RIGHT_SIDE{";
    content += serializeTrajectory(path.getRightWheelTrajectory(), RETURNTYPE.YVALUE);
    content += "};\n";
    content += "EXCEL READY PATH COORDANATES";
    content += "\n";
    content += returnPathCoordanates(path.getLeftWheelTrajectory(),path.getRightWheelTrajectory());
    return content;
  }
  
  private String returnPathCoordanates(Trajectory left, Trajectory right) {
	  String values = "";
	  for(int i =0; i < left.getNumSegments(); i++) {
		  Segment leftsegment = left.getSegment(i);
		  Segment rightsegment = right.getSegment(i);
		  values += leftsegment.x + "," + leftsegment.y + "," + rightsegment.x + "," + rightsegment.y + "\n";  
	  }
	  return values;
  }
  
  private String serializeTrajectory(Trajectory trajectory , RETURNTYPE returntype) {
    String content = "";
    for (int i = 0; i < trajectory.getNumSegments(); ++i) {
      String temp = "";
      Segment segment = trajectory.getSegment(i);
      if(returntype == RETURNTYPE.HEADINGR || returntype == RETURNTYPE.HEADINGD || returntype == RETURNTYPE.VELOCITY) {
    	  content += "{";
          content += segment.pos;
          content += ",";
      }
      switch(returntype) {
      	case VELOCITY:
      		content += segment.vel;
      		break;
      	case XVALUE:
      		content += segment.x;
      		break;
      	case YVALUE:
      		content += segment.y;
      		break;
      	case JERK:
      		content += segment.jerk;
      		break;
      	case DT:
      		content += segment.dt;
      		break;
      	case HEADINGR:
      		content += segment.heading;
      		break;
      	case HEADINGD:
      		if(segment.heading * 180/Math.PI > 180) {
      			content += -(360 - (segment.heading * 180/Math.PI));
      		}
      		else {
      			content += segment.heading * 180/Math.PI;
      		}
       		break;
      	case ACCELERATION:
      		content += segment.acc;
      		break;
      	default:
      		System.out.println("SOMETHING HAS GONE WRONG");
      }
      if (returntype == RETURNTYPE.HEADINGR || returntype == RETURNTYPE.HEADINGD || returntype == RETURNTYPE.VELOCITY) {
    	  content += "},";
      }else {
    	  content += ",";
      }
    }
    if (!(returntype == RETURNTYPE.HEADINGR) && !(returntype == RETURNTYPE.HEADINGD) && !(returntype == RETURNTYPE.VELOCITY)) {
    	content.substring(0, content.length()-1);
    }
    return content;
  }
  
}
