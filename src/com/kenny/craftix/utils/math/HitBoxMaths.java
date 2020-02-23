package com.kenny.craftix.utils.math;

import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.collision.HitBox;

public class HitBoxMaths {

	public static boolean isColliding(HitBox box1, HitBox box2) {
		HitBox[] boxes = { box1, box2 };
		boolean isBox = false;

			if(isColliding(true, boxes))
			{
				isBox = true;
			}
			if(isColliding(false, boxes))
			{
				isBox = true;
			}
		
		return isBox;
	}

	private static boolean isColliding(boolean b, HitBox[] boxes) {
		boolean inBox = false;
		int i1 = 0;
		int i2 = 0;
		if(b) {
			i1 = 0;
			i2 = 1;
		} else {
			i1 = 1;
			i2 = 0;
		}
		
		HitBox checker = boxes[i1];
		HitBox checked = boxes[i2];
		
		Vector3f[] positions = getNormalizedPosYrot(checker, checked);
		
		Vector3f checkerPos = positions[i1];
		Vector3f checkedPos = positions[i2];
		
		if(true)
		{
			for (Vector3f vec : checked.corners) {
				float checkedX = checkerPos.x + vec.x;
				float checkedZ = checkerPos.z + vec.z;
				float checkerXmin = checkedPos.x + checked.xMin;
				float checkerXmax = checkedPos.x + checked.xMax;
				float checkerZmin = checkedPos.z + checked.zMin;
				float checkerZmax = checkedPos.z + checked.zMax;
				if(checkedX < checkerXmax && checkedX > checkerXmin) {
					if(checkedZ < checkerZmax && checkedZ > checkerZmin) {
						inBox = true;
					}
				}
			}
		}
		
		
		return inBox;
	}

	private static Vector3f[] getNormalizedPosYrot(HitBox checker, HitBox checked) {
		Vector3f[] returnVec = new Vector3f[2];
		Vector3f boxA = checker.getPosition();
		Vector3f boxB = checked.getPosition();
		
		float rotYA = checker.getRotation().y;
		float rotYB = checked.getRotation().y;
		
		/**Rotate a box*/
		float xOff = boxB.x - boxA.x;
		float zOff = boxB.x - boxA.x;
		
		float distance = (float) Math.sqrt(xOff * xOff + zOff * zOff);
		
		float xCalc1 = (float) (distance * Math.sin(rotYA));
		float zCalc1 = (float) (distance * Math.cos(rotYA));
		
		Vector3f bNewPos = new Vector3f(boxA.x - xCalc1, boxB.y, boxA.z - zCalc1);
		
		float xCalc2 = (float) (distance * Math.sin(rotYB + 90));
		float zCalc2 = (float) (distance * Math.cos(rotYB + 90));
		
		Vector3f aNewPos = new Vector3f(boxB.x - xCalc2, boxA.y, boxB.z - zCalc2);
		
		returnVec[0] = aNewPos;
		returnVec[1] = bNewPos;
		return returnVec;
	}

}