package com.assignment5.model;

/**
 * Refactoring: Replace Magic Strings with Symbolic Constants
 * Replaced hardcoded "PDF", "CSV" strings with enum
 * Benefits: Type safety, prevents typos, easier to extend
 */
public enum ReportType {
    PDF,
    CSV,
    TEXT
}
