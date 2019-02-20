
#import "RNNumberPickerLibrary.h"
#import "ActionSheetPicker.h"

@implementation RNNumberPickerLibrary{NSMutableArray *qtyArray; RCTResponseSenderBlock onDoneClick; RCTResponseSenderBlock onCancelClick;}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(createDialog:(NSDictionary *)indic createDialog:(RCTResponseSenderBlock)doneCallback createDialog:(RCTResponseSenderBlock)cancelCallback){
    int minValue=[indic[@"minValue"] intValue];
    int maxValue=[indic[@"maxValue"] intValue];
    onDoneClick = doneCallback;
    onCancelClick = cancelCallback;
    //  NSString *doneText=indic[@"doneText"];
    //  NSString *doneTextColor=indic[@"doneTextColor"];
    //  NSString *cancelText=indic[@"cancelText"];
    //  NSString *cancelTextColor=indic[@"cancelTextColor"];
    
    qtyArray = [[NSMutableArray alloc] init];
    for (int i = minValue; i <= maxValue; i+= 1) {
        [qtyArray addObject:[NSString stringWithFormat:@"%d",i]];
    }
    int selectedValue = 0;
    if(indic[@"selectedValue"]) {
        selectedValue = [indic[@"selectedValue"] intValue];
    }
    
    ActionSheetStringPicker* qtyPicker = [[ActionSheetStringPicker alloc]initWithTitle:@"" rows:qtyArray initialSelection:selectedValue target:self successAction:@selector(didSelectValue:element:) cancelAction:@selector(cancelActionSheet:) origin:self];
    dispatch_async(dispatch_get_main_queue(), ^{
        [qtyPicker showActionSheetPicker];
    });
}

- (void)didSelectValue:(NSNumber *)selectedIndex element:(id)element
{
    int qty = [[qtyArray objectAtIndex:[selectedIndex intValue]]intValue];
    NSString *qtyString = [NSString stringWithFormat:@"%i", qty];
    NSArray *events = @[qtyString];
    onDoneClick(@[[NSNull null], events]);
}

-(void) cancelActionSheet:(id)sender{
    NSArray *events = @[];
    onCancelClick(@[[NSNull null], events]);
}

@end
  
