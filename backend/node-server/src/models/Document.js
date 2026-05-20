const mongoose = require('mongoose');

const documentSchema = new mongoose.Schema({
  filename: {
    type: String,
    required: true
  },
  originalName: {
    type: String,
    required: true
  },
  fileType: {
    type: String,
    required: true,
    enum: ['docx', 'pdf', 'xlsx', 'pptx', 'doc', 'xls', 'ppt', 
           'odt', 'ods', 'odp', 'txt', 'rtf', 'csv', 'json', 'xml',
           'jpg', 'png', 'gif', 'bmp', 'svg', 'webp']
  },
  owner: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  fileSize: {
    type: Number,
    required: true
  },
  filePath: {
    type: String,
    required: true
  },
  mimeType: {
    type: String,
    default: null
  },
  description: {
    type: String,
    default: null
  },
  tags: [String],
  status: {
    type: String,
    enum: ['active', 'archived', 'deleted'],
    default: 'active'
  },
  isShared: {
    type: Boolean,
    default: false
  },
  sharedWith: [{
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    permission: { type: String, enum: ['view', 'edit', 'comment'], default: 'view' },
    sharedAt: { type: Date, default: Date.now }
  }],
  versions: [{
    versionNumber: Number,
    fileSize: Number,
    filePath: String,
    createdBy: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    createdAt: { type: Date, default: Date.now },
    changeLog: String
  }],
  comments: [{
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    text: String,
    createdAt: { type: Date, default: Date.now },
    likes: Number
  }],
  previewUrl: {
    type: String,
    default: null
  },
  textContent: {
    type: String,
    default: null // For OCR results
  },
  metadata: {
    author: String,
    subject: String,
    keywords: [String],
    pages: Number,
    width: Number,
    height: Number
  },
  favorites: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }],
  viewCount: {
    type: Number,
    default: 0
  },
  downloads: {
    type: Number,
    default: 0
  },
  lastAccessedBy: {
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    accessedAt: { type: Date, default: Date.now }
  },
  createdAt: {
    type: Date,
    default: Date.now
  },
  updatedAt: {
    type: Date,
    default: Date.now
  }
}, { timestamps: true });

// Index for faster queries
documentSchema.index({ owner: 1, status: 1 });
documentSchema.index({ createdAt: -1 });
documentSchema.index({ 'sharedWith.userId': 1 });
documentSchema.index({ tags: 1 });

module.exports = mongoose.model('Document', documentSchema);
