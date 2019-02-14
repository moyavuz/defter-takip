import { IEkip } from 'app/shared/model/ekip.model';
import { IBirim } from 'app/shared/model/birim.model';
import { IPoz } from 'app/shared/model/poz.model';
import { IProje } from 'app/shared/model/proje.model';
import { IMalzeme } from 'app/shared/model/malzeme.model';

export interface IIscilik {
    id?: number;
    miktar?: number;
    aciklama?: any;
    resimContentType?: string;
    resim?: any;
    ekip?: IEkip;
    birim?: IBirim;
    pozs?: IPoz[];
    projes?: IProje[];
    malzemes?: IMalzeme[];
}

export class Iscilik implements IIscilik {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklama?: any,
        public resimContentType?: string,
        public resim?: any,
        public ekip?: IEkip,
        public birim?: IBirim,
        public pozs?: IPoz[],
        public projes?: IProje[],
        public malzemes?: IMalzeme[]
    ) {}
}
