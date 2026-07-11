# ADR-0004: Marketplace Owns Compatibility Verification

- **Status:** Accepted
- **Date:** 2026-07-11

## Context

GitHub Actions and JetBrains Marketplace can both run Plugin Verifier checks.

Running the same multi-IDE verification in every pull request and again for a GitHub Release adds several minutes without improving the Marketplace submission.

The first MarkdownNeat version was uploaded manually to establish the Marketplace vendor and plugin record.

Marketplace then performs wider compatibility verification and manually approves every new plugin version.

## Decision

The release pipeline has three separate responsibilities.

- Pull request CI MUST run fast tests, build the plugin, and validate its configuration and structure.
- A GitHub Draft Release MUST build the tagged source, run the same fast package validation, and attach the resulting ZIP.
- The Draft Release tag and title MUST both match the version declared by the tagged source.
- The Draft Release workflow MUST upload that exact ZIP to JetBrains Marketplace with the plugin XML ID and a repository secret named `JETBRAINS_MARKETPLACE_TOKEN`.

JetBrains Marketplace MUST own compatibility verification and manual approval.

GitHub Releases MUST remain drafts until the workflow succeeds, and Marketplace approval MUST NOT be automated.

## Consequences

- Pull requests and release drafts avoid duplicate multi-IDE Plugin Verifier runs.
- Marketplace receives the same ZIP attached to the GitHub Draft Release.
- A missing or rejected Marketplace token fails the Draft Release workflow before the draft is published.
- The permanent Marketplace token is stored only as a GitHub repository secret and is never committed or logged.
- The first manual Marketplace upload remains the bootstrap step; later releases can use the automated update API.
