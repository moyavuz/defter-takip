import { IIscilik } from 'app/shared/model/iscilik.model';
import { IPoz } from 'app/shared/model/poz.model';

export interface IIscilikDetay {
    id?: number;
    miktar?: number;
    iscilik?: IIscilik;
    poz?: IPoz;
}

export class IscilikDetay implements IIscilikDetay {
    constructor(public id?: number, public miktar?: number, public iscilik?: IIscilik, public poz?: IPoz) {}
}
