import { IEkip } from 'app/shared/model/ekip.model';
import { IPoz } from 'app/shared/model/poz.model';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { IProje } from 'app/shared/model/proje.model';

export interface IIscilik {
    id?: number;
    miktar?: number;
    aciklamaContentType?: string;
    aciklama?: any;
    aktifMi?: boolean;
    calisanEkip?: IEkip;
    pozs?: IPoz[];
    malzemes?: IMalzeme[];
    proje?: IProje;
}

export class Iscilik implements IIscilik {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklamaContentType?: string,
        public aciklama?: any,
        public aktifMi?: boolean,
        public calisanEkip?: IEkip,
        public pozs?: IPoz[],
        public malzemes?: IMalzeme[],
        public proje?: IProje
    ) {
        this.aktifMi = this.aktifMi || false;
    }
}
