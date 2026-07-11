import DOMPurify from "dompurify";
import { marked } from "marked";

export interface RenderRequest {
  version: 1;
  source: string;
  baseUrl: string;
  theme: "light" | "dark";
}

export interface RenderResult {
  html: string;
}

export function renderMarkdown(request: RenderRequest): RenderResult {
  const unsafeHtml = marked.parse(request.source, {
    async: false,
    gfm: true,
  });

  const sanitizedHtml = DOMPurify.sanitize(unsafeHtml, {
    FORBID_ATTR: ["style"],
    FORBID_TAGS: ["button", "form", "iframe", "object", "select", "style", "textarea"],
  });

  const template = document.createElement("template");
  template.innerHTML = sanitizedHtml;
  for (const input of template.content.querySelectorAll("input")) {
    if (input.type !== "checkbox") {
      input.remove();
      continue;
    }
    input.disabled = true;
    input.removeAttribute("name");
    input.removeAttribute("value");
  }

  const headingCounts = new Map<string, number>();
  for (const heading of template.content.querySelectorAll("h1, h2, h3, h4, h5, h6")) {
    const baseSlug = githubSlug(heading.textContent ?? "");
    const duplicateIndex = headingCounts.get(baseSlug) ?? 0;
    headingCounts.set(baseSlug, duplicateIndex + 1);
    heading.id = duplicateIndex === 0 ? baseSlug : `${baseSlug}-${duplicateIndex}`;
  }

  return {
    html: template.innerHTML,
  };
}

function githubSlug(value: string): string {
  return value
    .trim()
    .toLowerCase()
    .replace(/[^\p{L}\p{M}\p{N}\s_-]/gu, "")
    .replace(/\s/g, "-");
}
