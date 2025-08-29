package com.fastwebhook.sql;

/**
 * Put your final, single-statement SQL answers here.
 * Q1 for ODD last-two-digits, Q2 for EVEN last-two-digits.
 */
public final class Queries {
  private Queries(){}

  // STEP 1: Replace this with your actual Question 1 SQL
  // Example: If Question 1 asks "Find all students with GPA > 3.5"
  public static final String Q1_SQL = 
      "SELECT * FROM students WHERE gpa > 3.5";

  // STEP 2: Replace this with your actual Question 2 SQL  
  // Example: If Question 2 asks "Count students by department"
  public static final String Q2_SQL = 
      "SELECT department, COUNT(*) as total_students FROM students GROUP BY department";
}