#import "SketchView.h"
#import "PenSketchTool.h"
#import "EraserSketchTool.h"

@implementation SketchView
{
    SketchTool *currentTool;
    SketchTool *penTool;
    SketchTool *eraseTool;
    
    UIImage *incrementalImage;
    NSString *originalImagePath;
}

-(instancetype)initWithCoder:(NSCoder *)aDecoder
{
    self = [super initWithCoder:aDecoder];
    [self initialize];
    return self;
}

- (void) initialize
{
    [self setMultipleTouchEnabled:NO];
    penTool = [[PenSketchTool alloc] initWithTouchView:self];
    eraseTool = [[EraserSketchTool alloc] initWithTouchView:self];
    originalImagePath = nil;
    
    [self setToolType:SketchToolTypePen];
    
    [self setBackgroundColor:[UIColor clearColor]];
}

-(void)setToolType:(SketchToolType) toolType
{
    switch (toolType) {
        case SketchToolTypePen:
            currentTool = penTool;
            break;
        case SketchToolTypeEraser:
            currentTool = eraseTool;
            break;
        default:
            currentTool = penTool;
            break;
    }
}

-(void)setToolColor:(UIColor *)rgba
{
    [(PenSketchTool *)penTool setToolColor:rgba];
}

-(void)setViewImage:(UIImage *)image
{
    incrementalImage = image;
    [self setNeedsDisplay];
}
    
-(void)setToolThickness:(CGFloat)toolThickness
{
    [(PenSketchTool *)penTool setToolThickness:toolThickness];
}

-(void)setViewImagePath:(NSString *)image
{
    originalImagePath = image;
}

-(void) clear
{
    incrementalImage = nil;
    if(originalImagePath != nil) {
        UIImage *image = [UIImage imageWithContentsOfFile:originalImagePath];
        incrementalImage = image;
    }
    [currentTool clear];
    [self setNeedsDisplay];
}

// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    CGSize size = CGSizeMake(rect.size.width, rect.size.height);
    UIImage * scaled = [self scaleImage: incrementalImage toSize:size];
    // Drawing code
    [scaled drawInRect:rect];
    [currentTool render];
}

-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [currentTool touchesBegan:touches withEvent:event];
}

-(void)touchesMoved:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [currentTool touchesMoved:touches withEvent:event];
}

-(void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [currentTool touchesEnded:touches withEvent:event];
    [self takeSnapshot];
}

-(void)touchesCancelled:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [currentTool touchesCancelled:touches withEvent:event];
    [self takeSnapshot];
}

-(void)takeSnapshot
{
    [self setViewImage:[self drawBitmap]];
    [currentTool clear];
}

-(UIImage *)drawBitmap
{
    UIGraphicsBeginImageContextWithOptions(self.bounds.size, NO, 0.0);
    [self drawRect:self.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
}

- (UIImage *)scaleImage:(UIImage *)originalImage toSize:(CGSize)size
{
    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
    CGContextRef context = CGBitmapContextCreate(NULL, size.width, size.height, 8, 0, colorSpace, kCGImageAlphaPremultipliedLast);
    CGContextClearRect(context, CGRectMake(0, 0, size.width, size.height));
    
    if (originalImage.imageOrientation == UIImageOrientationRight) {
        CGContextRotateCTM(context, -M_PI_2);
        CGContextTranslateCTM(context, -size.height, 0.0f);
        CGContextDrawImage(context, CGRectMake(0, 0, size.height, size.width), originalImage.CGImage);
    } else {
        CGContextDrawImage(context, CGRectMake(0, 0, size.width, size.height), originalImage.CGImage);
    }
    
    CGImageRef scaledImage = CGBitmapContextCreateImage(context);
    CGColorSpaceRelease(colorSpace);
    CGContextRelease(context);
    
    UIImage *image = [UIImage imageWithCGImage:scaledImage];
    CGImageRelease(scaledImage);
    
    return image;
}

@end
