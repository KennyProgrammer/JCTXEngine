package com.kenny.craftix.client.collision;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;

public class CollisionDetector 
{
	/**This is position in real space.*/
	private Vector3f elipsoidPositionIR;
	private Vector3f elipsoidPositionIE;
	/**Triangle point in R space*/
	private Vector3f triangleP1R;
	private Vector3f triangleP2R;
	private Vector3f triangleP3R;
	/**Triangle point in E space*/
	private Vector3f triangleP1E;
	private Vector3f triangleP2E;
	private Vector3f triangleP3E;
	/**Vector starting at P2 going to P1*/
	private Vector3f P2P1E;
	/**Vector starting at P2 going to P3*/
	private Vector3f P2P3E;
	/**Plane Intersection in point at R space*/
	@SuppressWarnings("unused")
	private Vector3f planeIntersectionPointR;
	/**Plane Intersection in point at E space*/
	private Vector3f planeIntersectionPointE;
	/**Plane normals*/
	private Vector3f planeNormal;
	private Vector3f planeUnitNormal;
	private float planeConstantD;
	private float planeConstantP;
	/**Its collision detect distance*/
	private float distance;
	private float t0;
	private float t1;
	/**This is velocity in R space*/
	private Vector3f velocityR;
	/**This is velocity in E space*/
	private Vector3f velocityE;
	/**Its a Matrix calulation*/
	private Matrix3f toESpaceMatirx = new Matrix3f();
	@SuppressWarnings("unused")
	private Matrix3f fromESpaceMatrix;
	/**Collision for vertex*/
	private CollisionPocket collisionPocketVertex;
	/**Collision for surface*/
	@SuppressWarnings("unused")
	private CollisionPocket collisionPocketSurface;
	/**Collision for edge*/
	private CollisionPocket collisionPocketEdge;
	
	public CollisionDetector(Vector3f position, Vector3f velocity, Vector3f p1, Vector3f p2,
			Vector3f p3, float elipsoidMaxX, float elipsoidMaxY, float elipsoidMaxZ)
	{
		this.elipsoidPositionIR = position;
		this.velocityR = velocity;
		this.triangleP1R = p1;
		this.triangleP2R = p2;
		this.triangleP3R = p3;
		
		this.toESpaceMatirx.m00 = (1 / elipsoidMaxX);
		this.toESpaceMatirx.m11 = (1 / elipsoidMaxY);
		this.toESpaceMatirx.m22 = (1 / elipsoidMaxZ);
		
	}
	
	public void sendValuesToESpace()
	{
		Matrix3f.transform(toESpaceMatirx, triangleP1R, triangleP1E);
		Matrix3f.transform(toESpaceMatirx, triangleP2R, triangleP2E);
		Matrix3f.transform(toESpaceMatirx, triangleP3R, triangleP3E);
		
		Matrix3f.transform(toESpaceMatirx, velocityR, velocityE);
		Matrix3f.transform(toESpaceMatirx, elipsoidPositionIR, elipsoidPositionIE);
	}
	
	public void constructCollisionValues()
	{
		this.P2P1E = Vector3f.sub(triangleP1E, triangleP2E, P2P1E);
		this.P2P3E = Vector3f.sub(triangleP3E, triangleP2E, P2P3E);
		
		this.planeNormal = Vector3f.cross(P2P3E, P2P1E, planeNormal);
		
		
		this.planeConstantD = (planeNormal.x * triangleP2E.x * -1) + 
				(planeNormal.y * triangleP2E.y * -1) + (planeNormal.z * triangleP2E.z * -1);
		this.planeConstantP = (float) (planeConstantD / (Math.sqrt((planeNormal.x * planeNormal.x) + 
				(planeNormal.y * planeNormal.y) + (planeNormal.z * planeNormal.z))));
		this.planeUnitNormal = planeNormal.normalise(planeUnitNormal);
		this.distance = Vector3f.dot(planeUnitNormal, elipsoidPositionIE) + planeConstantP;
		
		if(Vector3f.dot(planeUnitNormal, velocityE) == 0.0)
		{
			t0 = 0; t1 = 1; System.out.println("Object Moving pallery to plane");
		}
		else {
		
		try 
		{
			this.t0 = (1 - distance) / (Vector3f.dot(planeUnitNormal, velocityE));
		} catch (IllegalArgumentException e) {t0 = 0; t1 = 1; System.out.println("Object Moving pallery to plane");}
		
		try 
		{	
			this.t1 = (-1 - distance) / (Vector3f.dot(planeUnitNormal, velocityE));
		} catch (IllegalArgumentException e) {t1 = 1; t0 = 0; System.out.println("Object Moving pallery to plane");}
		
		
		}
		
		if(t1 < t0)
		{
			float temp = t1;
			t1 = t0;
			t0 = temp;
			
		}
	}
	
	public CollisionPocket detectCollision()
	{
		sendValuesToESpace();
		constructCollisionValues();
		
		if(Vector3f.dot(planeUnitNormal, velocityE) == 0.0)
		{
			if(Math.abs(distance) > 1)
			{
				return null;
			}
		}
		
		else if(((t0 < 0) || (t0 > 1)) && ((t1 < 0) || (t1 > 1)))
		{
			return null;
		}
		
		planeIntersectionPointE = Vector3f.sub(elipsoidPositionIE, (Vector3f) planeNormal, 
				planeIntersectionPointE);
		Vector3f velocityExt0 = new Vector3f(velocityE.x * t0, velocityE.y * t0, velocityE.z * t0);
		planeIntersectionPointE = Vector3f.add(planeIntersectionPointE, velocityExt0, 
				planeIntersectionPointE);
		
		if(checkPositionWithTriangle(planeIntersectionPointE, triangleP1E, triangleP2E, triangleP3E))
		{
			return collisionPocketSurface = new CollisionPocket(planeIntersectionPointE, distance, 0);
		}
		else 
		{	
			Vector3f elipsoidVertexDistance = new Vector3f();
			Vector3f vertexElipsoidDistance = new Vector3f();
			
			float vertexTime1;
			float vertexTime2;
			float smallestSolutionVertex = 10005;
			
			Vector3f collisionPoint = new Vector3f();
			
			float a = (float) velocityE.lengthSquared();
			
			elipsoidVertexDistance = Vector3f.sub(elipsoidPositionIE, triangleP1E, 
					elipsoidVertexDistance);
			
			float b = 2 * Vector3f.dot(velocityE, elipsoidVertexDistance);
			
			vertexElipsoidDistance = Vector3f.sub(triangleP1E, elipsoidPositionIE, 
					vertexElipsoidDistance);
			
			float c = (vertexElipsoidDistance.lengthSquared()) - 1;
			
			if(((b * b) - (4 * a * c)) >= 0)
			{
				vertexTime1 = (-b + (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				vertexTime2 = (-b - (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				
				if((vertexTime1 < vertexTime2) && (vertexTime1 <= t1) && (vertexTime1 >= t0) && 
						((vertexTime1 < smallestSolutionVertex) || (smallestSolutionVertex == 10005)))
				{
					smallestSolutionVertex = vertexTime1;
					collisionPoint = triangleP1E;
				} else if ((vertexTime2 < vertexTime1) && (vertexTime2 <= t1) && (vertexTime2 >= t0) && 
						((vertexTime2 < smallestSolutionVertex) || (smallestSolutionVertex == 10005)))
				{
					smallestSolutionVertex = vertexTime2;
					collisionPoint = triangleP1E;
				}
			}
			
			elipsoidVertexDistance = Vector3f.sub(elipsoidPositionIE, triangleP2E, 
					elipsoidVertexDistance);
			
			b = 2 * Vector3f.dot(velocityE, elipsoidVertexDistance);
			
			vertexElipsoidDistance = Vector3f.sub(triangleP2E, elipsoidPositionIE, 
					vertexElipsoidDistance);
			
			c = (vertexElipsoidDistance.lengthSquared()) - 1;
			
			if(((b * b) - (4 * a * c)) >= 0)
			{
				vertexTime1 = (-b + (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				vertexTime2 = (-b - (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				
				if((vertexTime1 < vertexTime2) && (vertexTime1 <= t1) && (vertexTime1 >= t0) && 
						((vertexTime1 < smallestSolutionVertex) || (smallestSolutionVertex == 10005)))
				{
					smallestSolutionVertex = vertexTime1;
					collisionPoint = triangleP2E;
				} else if ((vertexTime2 < vertexTime1) && (vertexTime2 <= t1) && (vertexTime2 >= t0) && 
						((vertexTime2 < smallestSolutionVertex) || (smallestSolutionVertex == 10005)))
				{
					smallestSolutionVertex = vertexTime2;
					collisionPoint = triangleP2E;
				}
			}
			
			
			elipsoidVertexDistance = Vector3f.sub(elipsoidPositionIE, triangleP3E, 
					elipsoidVertexDistance);
			
			b = 2 * Vector3f.dot(velocityE, elipsoidVertexDistance);
			
			vertexElipsoidDistance = Vector3f.sub(triangleP3E, elipsoidPositionIE, 
					vertexElipsoidDistance);
			
			c = (vertexElipsoidDistance.lengthSquared()) - 1;
			
			if(((b * b) - (4 * a * c)) >= 0)
			{
				vertexTime1 = (-b + (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				vertexTime2 = (-b - (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				
				if((vertexTime1 < vertexTime2) && (vertexTime1 <= t1) && (vertexTime1 >= t0) && 
						((vertexTime1 < smallestSolutionVertex) || (smallestSolutionVertex == 10005)))
				{
					smallestSolutionVertex = vertexTime1;
					collisionPoint = triangleP3E;
				} else if ((vertexTime2 < vertexTime1) && (vertexTime2 <= t1) && (vertexTime2 >= t0) && 
						((vertexTime2 < smallestSolutionVertex) || (smallestSolutionVertex == 10005)))
				{
					smallestSolutionVertex = vertexTime2;
					collisionPoint = triangleP3E;
				}
			}
			
			if(smallestSolutionVertex != 10005)
			{
				float vertexDistance = smallestSolutionVertex * velocityE.length();
				collisionPocketVertex = new CollisionPocket(collisionPoint, vertexDistance, 2);
			}
			
			Vector3f edge = new Vector3f();
			Vector3f baseToVertex = new Vector3f();
			Vector3f edgeIntersectionPoint = new Vector3f();
			
			float intersectionDistance;
			Vector3f fromEdgePoint = new Vector3f();
			Vector3f smallestEdge = new Vector3f();
			
			float edgeTime1;
			float edgeTime2;
			float smallestSolutionEdge = 10005;
			float smallerSolutionEdge;
			float smallerF = -1;
			float smallestF = 0;
			
			edge = Vector3f.sub(triangleP2E, triangleP1E, edge);
			baseToVertex = Vector3f.sub(triangleP1E, elipsoidPositionIE, baseToVertex);
			a = edge.lengthSquared() * -1 * velocityE.lengthSquared() + (Vector3f.dot(edge, velocityE) * Vector3f.dot(edge, velocityE));
			b = edge.lengthSquared() * 2 * Vector3f.dot(velocityE, baseToVertex) - (2* Vector3f.dot(edge,velocityE) * Vector3f.dot(edge, baseToVertex));
			c = edge.lengthSquared() * (1 - baseToVertex.lengthSquared()) + (Vector3f.dot(edge, baseToVertex) * Vector3f.dot(edge, baseToVertex));

			if(((b * b) - (4 * a * c)) >= 0)
			{
				edgeTime1 = (-b + (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				edgeTime2 = (-b - (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				
				if(edgeTime1 <= edgeTime2) 
				{
					smallerSolutionEdge = edgeTime1;
				}else {
					smallerSolutionEdge = edgeTime2;
				}
				
				if(smallerSolutionEdge >= 0 && smallerSolutionEdge <= 1)
				{
					smallerF = ((Vector3f.dot(edge, velocityE) * smallerSolutionEdge) - 
							Vector3f.dot(edge, baseToVertex)) / edge.lengthSquared();
				}
				
				if((smallerF) >= 0 && (smallerF <= 1) && (smallerSolutionEdge < smallestSolutionEdge))
				{
					smallestF = smallerF;
					smallestSolutionEdge = smallerSolutionEdge;
					fromEdgePoint = triangleP1E;
					smallestEdge = edge;
				}
			}
			/**P2E*/
			edge = Vector3f.sub(triangleP3E, triangleP2E, edge);
			baseToVertex = Vector3f.sub(triangleP2E, elipsoidPositionIE, baseToVertex);
			a = edge.lengthSquared() * -1 * velocityE.lengthSquared() + (Vector3f.dot(edge, velocityE) * Vector3f.dot(edge, velocityE));
			b = edge.lengthSquared() * 2 * Vector3f.dot(velocityE, baseToVertex) - (2* Vector3f.dot(edge,velocityE) * Vector3f.dot(edge, baseToVertex));
			c = edge.lengthSquared() * (1 - baseToVertex.lengthSquared()) + (Vector3f.dot(edge, baseToVertex) * Vector3f.dot(edge, baseToVertex));

			if(((b * b) - (4 * a * c)) >= 0)
			{
				edgeTime1 = (-b + (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				edgeTime2 = (-b - (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				
				if(edgeTime1 <= edgeTime2) 
				{
					smallerSolutionEdge = edgeTime1;
				}else {
					smallerSolutionEdge = edgeTime2;
				}
				
				if((smallerSolutionEdge >= 0) && smallerSolutionEdge <= 1)
				{
					smallerF = ((Vector3f.dot(edge, velocityE) * smallerSolutionEdge) - 
							Vector3f.dot(edge, baseToVertex)) / edge.lengthSquared();
				}
				
				if((smallerF) >= 0 && (smallerF <= 1) && (smallerSolutionEdge < smallestSolutionEdge))
				{
					smallestF = smallerF;
					smallestSolutionEdge = smallerSolutionEdge;
					fromEdgePoint = triangleP2E;
					smallestEdge = edge;
				}
			}
			/**P1E*/
			edge = Vector3f.sub(triangleP1E, triangleP3E, edge);
			baseToVertex = Vector3f.sub(triangleP3E, elipsoidPositionIE, baseToVertex);
			a = edge.lengthSquared() * -1 * velocityE.lengthSquared() + (Vector3f.dot(edge, velocityE) * Vector3f.dot(edge, velocityE));
			b = edge.lengthSquared() * 2 * Vector3f.dot(velocityE, baseToVertex) - (2* Vector3f.dot(edge,velocityE) * Vector3f.dot(edge, baseToVertex));
			c = edge.lengthSquared() * (1 - baseToVertex.lengthSquared()) + (Vector3f.dot(edge, baseToVertex) * Vector3f.dot(edge, baseToVertex));

			if(((b * b) - (4 * a * c)) >= 0)
			{
				edgeTime1 = (-b + (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				edgeTime2 = (-b - (float) Math.sqrt((double) ((b * b) - (4 * a * c))) / (2 * a));
				
				if(edgeTime1 <= edgeTime2) 
				{
					smallerSolutionEdge = edgeTime1;
				}else {
					smallerSolutionEdge = edgeTime2;
				}
				
				if((smallerSolutionEdge >= 0) && smallerSolutionEdge <= 1)
				{
					smallerF = ((Vector3f.dot(edge, velocityE) * smallerSolutionEdge) - 
							Vector3f.dot(edge, baseToVertex)) / edge.lengthSquared();
				}
				
				if((smallerF) >= 0 && (smallerF <= 1) && (smallerSolutionEdge < smallestSolutionEdge))
				{
					smallestF = smallerF;
					smallestSolutionEdge = smallerSolutionEdge;
					fromEdgePoint = triangleP3E;
					smallestEdge = edge;
				}
			}
			
			if(smallestSolutionEdge != 10005)
			{
				edgeIntersectionPoint = Vector3f.add(fromEdgePoint, (Vector3f) smallestEdge.scale(smallestF), 
						edgeIntersectionPoint);
				intersectionDistance = smallestSolutionEdge * velocityE.length();
				collisionPocketEdge = new  CollisionPocket(edgeIntersectionPoint, intersectionDistance, 3);
			}
			
		} /**End of the edge/vertex position of this methid.*/
		
		if((collisionPocketEdge != null) || (collisionPocketVertex != null)) {
			try {
				if(collisionPocketEdge.getCollisionDistance() < collisionPocketVertex.getCollisionDistance()) {
					return collisionPocketEdge;
				}
			} catch (NullPointerException e) {}
			try {
				if(collisionPocketVertex.getCollisionDistance() > collisionPocketEdge.getCollisionDistance()) {
					return collisionPocketVertex;
				}
			} catch (NullPointerException e) {}
			
			
			if(collisionPocketEdge != null) {
				try {
					return collisionPocketEdge;
					
				} catch (NullPointerException e) {}}
			if(collisionPocketVertex != null) {
				try {
					return collisionPocketVertex;
				} catch (NullPointerException e) {}
			    }
		}
		
		
		return null;
	}
	
	public boolean checkPositionWithTriangle(Vector3f position, Vector3f p1, Vector3f p2, Vector3f p3)
	{
		float angles = 0;
		
		Vector3f v1 = new Vector3f();
		Vector3f.sub(position, p1, v1);
		Vector3f v2 = new Vector3f();
		Vector3f.sub(position, p2, v2);
		Vector3f v3 = new Vector3f();
		Vector3f.sub(position, p3, v3);
		v1.normalise();
		v2.normalise();
		v3.normalise();
		
		angles += Math.acos(Vector3f.dot(v1, v2));
		angles += Math.acos(Vector3f.dot(v1, v3));
		angles += Math.acos(Vector3f.dot(v3, v2));
			return(Math.abs(angles - 2 * Math.PI) <= .005);
	}
}
