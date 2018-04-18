import { ToolType } from './index.d';
declare module "react-native-sketch-draw" {
  export interface SketchDraw {
    selectedTool: number
    toolColor: string
    localSourceImagePath: string
    toolThickness: number
    constants: Constants
  }

  export interface ImageResult {
    localFilePath: string
    imageWidth: number
    imageHeight: number
  }

  export interface Constants {
    toolType: Tool
  }

  export interface Tool {
    pen: ToolType
    eraser: ToolType
  }

  export interface ToolType {
    id: string
		name: string
  }

  export function onSaveSketch(imageResult: ImageResult): void;

  // export interface Options {
  //     cropping?: boolean;
  //     width?: number;
  //     height?: number;
  //     multiple?: boolean;
  //     path?: string;
  //     includeBase64?: boolean;
  //     includeExif?: boolean;
  //     cropperTintColor?: string;
  //     cropperCircleOverlay?: boolean;
  //     maxFiles?: number;
  //     waitAnimationEnd?: boolean;
  //     smartAlbums?: string[];
  //     useFrontCamera?: boolean;
  //     compressVideoPreset?: string;
  //     compressImageMaxWidth?: number;
  //     compressImageMaxHeight?: number;
  //     compressImageQuality?: number;
  //     loadingLabelText?: string;
  //     mediaType?: string;
  //     showsSelectedCount?: boolean;
  //     showCropGuidelines?: boolean;
  //     hideBottomControls?: boolean;
  //     enableRotationGesture?: boolean;
  // }

  // export function openPicker(options: Options): Promise<Image | Image[]>;
  // export function openCamera(options: Options): Promise<Image | Image[]>;
  // export function openCropper(options: Options): Promise<Image>;
  // export function clean(): Promise<void>;
  // export function cleanSingle(path: string): Promise<void>;

  // export interface ImageCropPicker {
  //     openPicker(options: Options): Promise<Image | Image[]>;
  //     openCamera(options: Options): Promise<Image | Image[]>;
  //     openCropper(options: Options): Promise<Image>;
  //     clean(): Promise<void>;
  //     cleanSingle(path: string): Promise<void>;
  // }

  const SketchDraw: SketchDraw;

  export default SketchDraw;
}
