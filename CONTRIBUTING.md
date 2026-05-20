# Contributing to Electrosoft

Thank you for your interest in contributing to Electrosoft! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Focus on the code, not the person
- Report any violations to the maintainers

## Getting Started

1. **Fork the repository** - Click the fork button on GitHub
2. **Clone your fork** - `git clone https://github.com/YOUR_USERNAME/Electrosoft.git`
3. **Create a branch** - `git checkout -b feature/your-feature-name`
4. **Make your changes** - Follow the guidelines below
5. **Commit your changes** - `git commit -m "Add your message"`
6. **Push to your fork** - `git push origin feature/your-feature-name`
7. **Create a Pull Request** - Describe your changes and reference any related issues

## Development Setup

### Backend Setup

```bash
cd backend/node-server
npm install
cp .env.example .env
npm run dev
```

### Python Service Setup

```bash
cd backend/python-service
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
pip install -r requirements.txt
python src/app.py
```

### Docker Setup

```bash
docker-compose up -d
```

## Code Guidelines

### JavaScript/Node.js

- Use ES6+ syntax
- Follow ESLint configuration
- Use camelCase for variables and functions
- Use PascalCase for classes
- Maximum line length: 100 characters
- Use meaningful variable names

### Python

- Follow PEP 8 style guide
- Use type hints where possible
- Maximum line length: 100 characters
- Use docstrings for functions and classes

### Kotlin

- Follow Kotlin style guide
- Use meaningful names
- Prefer val over var
- Use extension functions appropriately

## Testing

Before submitting a PR, ensure:

```bash
# Node.js tests
cd backend/node-server
npm test

# Python tests
cd backend/python-service
pytest

# Linting
npm run lint
```

## Commit Messages

Follow this format:

```
Type: Brief description (50 chars max)

Detailed explanation (72 chars max per line)
- Include motivation
- Explain what and why, not how
```

Types: feat, fix, docs, style, refactor, test, chore

Examples:
- `feat: Add document sharing with permissions`
- `fix: Resolve PDF conversion timeout issue`
- `docs: Update API documentation`

## Pull Request Process

1. Update documentation for new features
2. Add tests for new functionality
3. Ensure all tests pass
4. Update CHANGELOG.md
5. Provide clear PR description
6. Reference related issues
7. Wait for review and address feedback

## Issue Guidelines

### Reporting Bugs

Include:
- Clear, descriptive title
- Step-by-step reproduction
- Expected behavior
- Actual behavior
- Screenshots/logs if applicable
- Environment (OS, browser, versions)

### Feature Requests

Include:
- Clear description
- Use case and motivation
- Proposed solution
- Alternative solutions
- Additional context

## Documentation

- Update README.md for major changes
- Add inline code comments for complex logic
- Update API documentation for endpoint changes
- Include examples for new features

## Questions?

- Create an issue with the `question` label
- Check existing issues and discussions
- Contact the maintainers

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

Thank you for contributing! 🚀
